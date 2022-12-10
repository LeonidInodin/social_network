package ru.inodinln.social_network.services;


import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inodinln.social_network.entities.Post;
import ru.inodinln.social_network.entities.Subscription;
import ru.inodinln.social_network.entities.User;
import ru.inodinln.social_network.exceptions.businessException.NotFoundException;
import ru.inodinln.social_network.repositories.UserRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final SubscriptionService subscriptionService;

    private final ConversationService conversationService;

    private final PostService postService;

    public UserService(UserRepository userRepository, SubscriptionService subscriptionService,
                       @Lazy ConversationService conversationService, @Lazy PostService postService) {
        this.userRepository = userRepository;
        this.subscriptionService = subscriptionService;
        this.conversationService = conversationService;
        this.postService = postService;
    }
    ////////////////////////////Business methods section///////////////////////////////////////

    public List<User> getMembersByConversationId(Long conversationId, Integer page, Integer itemsPerPage){
        return userRepository.findUsersByConversationsContains
                (conversationService.getById(conversationId), PageRequest.of(page, itemsPerPage));
    }

    ////////////////////////////Statistics methods section///////////////////////////////////////

    public Map<User, Double> get10mostActive(LocalDate startOfPeriod, LocalDate endOfPeriod){
        long daysCount = ChronoUnit.DAYS.between(endOfPeriod, startOfPeriod);
        return postService.getPostsByTimestampBetween(startOfPeriod, endOfPeriod)
                .stream().collect(Collectors
                        .toMap(Post::getAuthor, (e) -> (double) postService.getPostsByUserId(e.getId()).size()/daysCount))
                .entrySet().stream().sorted(Map.Entry.<User, Double>comparingByValue().reversed())
                .limit(10).collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new));
    }

    @Transactional
    public void setRole(Long userId, Integer role) {
        getById(userId).setRoleId(role);
    }

    @Transactional
    public void increaseCountOfSubscr(Long userId) {
        User user = getById(userId);
        user.setCountOfSubscribers(user.getCountOfSubscribers() + 1L);
        save(user);
    }

    @Transactional
    public void decreaseCountOfSubscr(Long userId) {
        User user = getById(userId);
        user.setCountOfSubscribers(user.getCountOfSubscribers() - 1L);
        if (user.getCountOfSubscribers() < 0)
            user.setCountOfSubscribers(0L);
        save(user);
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////

    public List<User> getAll(Integer page, Integer itemsPerPage) {
        return userRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
    }

    public User getById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("User not found with id " + userId));
    }

    public User create(String firstName, String lastName, String eMail, String password, LocalDate birthDate) {
        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(eMail);
        newUser.setPassword(password);
        newUser.setBirthDate(birthDate);
        return save(newUser);
    }

    @Transactional
    public User save(User newUser) {
        return userRepository.save(newUser);
    }

    @Transactional
    public User update(Long id, String firstName, String lastName, String eMail, String password, LocalDate birthDate) {
        User userToBeUpdated = getById(id);
        userToBeUpdated.setFirstName(firstName);
        userToBeUpdated.setLastName(lastName);
        userToBeUpdated.setEmail(eMail);
        userToBeUpdated.setPassword(password);
        userToBeUpdated.setBirthDate(birthDate);
        return save(userToBeUpdated);
    }

    @Transactional
    public void delete(Long userId) {
        userRepository.delete(getById(userId));
    }

    ////////////////////////////Service methods section///////////////////////////////////////
    public List<User> getTargetsOfUserByUserId(Long userId) {
        return subscriptionService.getAllSubscriptionsByUser(userId)
                .stream().map(Subscription::getTarget).collect(Collectors.toList());
    }

}
