package ru.inodinln.social_network.services;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inodinln.social_network.entities.Subscription;
import ru.inodinln.social_network.entities.User;
import ru.inodinln.social_network.exceptions.DataException;
import ru.inodinln.social_network.exceptions.businessException.NotFoundException;
import ru.inodinln.social_network.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    SubscriptionService subscriptionService;

    public UserService(UserRepository userRepository, SubscriptionService subscriptionService) {
        this.userRepository = userRepository;
        this.subscriptionService = subscriptionService;
    }

    //get current user's subscribees list:
    public List<User> getSubscribeesOfUser(Long userId){
        List<Subscription> listOfSubscr = subscriptionService.getSubscriptionsByUser(userId);
        List<User> listOfUsers = new ArrayList<>(listOfSubscr.size());
        for (Subscription subscr : listOfSubscr) {
            User user = userRepository.findById(subscr.getToId()).orElseThrow(() ->
                    new NotFoundException("Subscription not found with id " + subscr.getToId()));
            listOfUsers.add(user);
        }
        return listOfUsers;
    }

    //Set user's role:
    @Transactional
    public void setRole(Long userId, Integer role) {
        findById(userId).setRoleId(role);
    }

    public void incrCountOfSubscr(Long userId){
        User user = findById(userId);
        user.setCountOfSubscribers(user.getCountOfSubscribers()+1);
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long userId){
        return userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("User not found with id " + userId));
    }

    @Transactional
    public User save(User newUser) {
        try {userRepository.save(newUser);
        }
        catch (DataIntegrityViolationException e) {
            throw new DataException(e.getMostSpecificCause().getMessage());
        }
        return findById(newUser.getId());
    }

    @Transactional
    public User update(User userToBeUpdated) {
        try {userRepository.save(userToBeUpdated);
        }
        catch (DataIntegrityViolationException e) {
            throw new DataException(e.getMostSpecificCause().getMessage());
        }
        return findById(userToBeUpdated.getId());
    }

    @Transactional
    public void delete(Long userId) {
        User userToBeDeleted = findById(userId);
        userRepository.delete(userToBeDeleted);
    }

}
