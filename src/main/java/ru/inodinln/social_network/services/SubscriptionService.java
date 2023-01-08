package ru.inodinln.social_network.services;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inodinln.social_network.entities.Subscription;
import ru.inodinln.social_network.exceptions.businessException.BusinessException;
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
    public List<Subscription> getSubscriptionsByUser(Long userId, Integer page, Integer itemsPerPage) {
        return subscriptionRepository.findBySubscriber(userService.getById(userId), PageRequest.of(page, itemsPerPage))
                .orElseThrow(() -> new NotFoundException("Not found subscriptions by user with id " + userId));
    }

    //Get all subscriptions to current user:
    public List<Subscription> getSubscriptionsToUser(Long userId, Integer page, Integer itemsPerPage) {
        return subscriptionRepository.findByTarget(userService.getById(userId), PageRequest.of(page, itemsPerPage))
                .orElseThrow(() -> new NotFoundException("Not found subscriptions to user with id " + userId));
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////

    public List<Subscription> getAll(Integer page, Integer itemsPerPage) {
        return subscriptionRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
    }

    public Subscription getById(long subscrId) {
        return subscriptionRepository.findById(subscrId).orElseThrow(() ->
                new NotFoundException("Not found subscription with id " + subscrId));
    }

    @Transactional
    public Subscription create(Long subscriberId, Long targetId) {
        if (subscriptionRepository.findBySubscriberAndTarget(userService.getById(subscriberId),
                userService.getById(targetId)) != null)
            throw new BusinessException("Subscription by user with id " + subscriberId
                    + " to user with id " + targetId + "is already exists");
        Subscription subscr = new Subscription();
        subscr.setSubscriber(userService.getById(subscriberId));
        subscr.setTarget(userService.getById(targetId));
        Subscription subscriptionForReturn = save(subscr);
        userService.increaseCountOfSubscr(targetId);
        return subscriptionForReturn;
    }

    @Transactional
    public Subscription save(Subscription subscription){
        return subscriptionRepository.save(subscription);
    }

    @Transactional
    public void delete(Long subscrId) {
        Subscription subscription = getById(subscrId);
        subscriptionRepository.delete(subscription);
        userService.decreaseCountOfSubscr(subscription.getTarget().getId());

    }

}
