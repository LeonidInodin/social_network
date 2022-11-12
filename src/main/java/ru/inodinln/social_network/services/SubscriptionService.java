package ru.inodinln.social_network.services;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inodinln.social_network.entities.Subscription;
import ru.inodinln.social_network.exceptions.DataException;
import ru.inodinln.social_network.exceptions.businessException.NotFoundException;
import ru.inodinln.social_network.repositories.SubscriptionRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    private final UserService userService;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, @Lazy UserService userService) {
        this.subscriptionRepository = subscriptionRepository;
        this.userService = userService;
    }
    ////////////////////////////Business methods section///////////////////////////////////////

    //Get all subscriptions by current user:
    public List<Subscription> getSubscriptionsByUser(Long userId) {
        return subscriptionRepository.findByFrom_IdIs(userId);
    }

    //Get all subscriptions to current user:
    public List<Subscription> getSubscriptionsToUser(Long userId) {
        return subscriptionRepository.findByToIdIs(userId);
    }

    //Check is exists new subscription
    public Subscription findByIdOfUsers(Long fromId, Long toId){
        return subscriptionRepository.findByFrom_IdAndToId(fromId, toId);
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////

    public List<Subscription> findAll() {
        return subscriptionRepository.findAll();
    }

    public Subscription findById(long subscrId) {
        return subscriptionRepository.findById(subscrId).orElseThrow(() ->
                new NotFoundException("Subscription not found with id " + subscrId));
    }

    @Transactional
    public void save(Long fromId, Long toId) {
        if (findByIdOfUsers(fromId, toId) == null) {
        Subscription subscr = new Subscription();
        subscr.setFrom(userService.findById(fromId));
        userService.incrCountOfSubscr(toId);
        subscr.setToId(toId);
        subscriptionRepository.save(subscr);}
        else throw new DataException("Database contains this subscription yet");
    }

    @Transactional
    public void delete(Long subscrId) {
        Subscription subscr = subscriptionRepository.findById(subscrId).orElseThrow(() ->
                new NotFoundException("Subscription not found with id " + subscrId));
        subscriptionRepository.delete(subscr);
    }

}
