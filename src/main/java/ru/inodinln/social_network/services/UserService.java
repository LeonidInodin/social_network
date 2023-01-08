package ru.inodinln.social_network.services;


import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inodinln.social_network.entities.User;
import ru.inodinln.social_network.exceptions.businessException.NotFoundException;
import ru.inodinln.social_network.exceptions.securityException.AuthorizationException;
import ru.inodinln.social_network.repositories.UserRepository;
import ru.inodinln.social_network.security.Roles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final ConversationService conversationService;

    private final PostService postService;

   private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       @Lazy ConversationService conversationService,
                       @Lazy PostService postService,
                       @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.conversationService = conversationService;
        this.postService = postService;
        this.passwordEncoder = passwordEncoder;
    }
    ////////////////////////////Business methods section///////////////////////////////////////

    public List<User> getMembersByConversationId(Long conversationId, Integer page, Integer itemsPerPage, String eMail) {
        List<User> resultList = userRepository.findUsersByConversationsContains
                (conversationService.getById(conversationId), PageRequest.of(page, itemsPerPage));
        if (!(resultList.contains(getByEmail(eMail))))
            throw new AuthorizationException("Forbidden to view with current credentials");
        return resultList;
    }

    ////////////////////////////Statistics methods section///////////////////////////////////////

    public List<Map.Entry<User, Long>> getMostActiveUsers(LocalDate startOfPeriod,
                                                          LocalDate endOfPeriod,
                                                          Integer page,
                                                          Integer itemsPerPage) {

        LocalDateTime start = startOfPeriod.atStartOfDay();
        LocalDateTime end = endOfPeriod.atStartOfDay();

        return userRepository.getMostActiveUsers(start, end, PageRequest.of(page, itemsPerPage))
                .orElseThrow(() -> new NotFoundException("Not found users for rating for this period "))
                .stream()
                .map((user) -> new java.util.AbstractMap.SimpleEntry<>
                        (user, postService.countPostsByAuthorAndTimestampBetween(user, start, end)))
                .collect(Collectors.toList());

    }

    ////////////////////////////Service methods section///////////////////////////////////////

    @Transactional
    public void setRole(Long userId, String role) {
        getById(userId).setRole(Roles.valueOf(role));
    }

    @Transactional
    public void increaseCountOfSubscr(Long userId) {
        User user = getById(userId);
        user.setCountOfSubscribers(user.getCountOfSubscribers() + 1L);
    }

    @Transactional
    public void decreaseCountOfSubscr(Long userId) {
        User user = getById(userId);
        user.setCountOfSubscribers(user.getCountOfSubscribers() - 1L);
        if (user.getCountOfSubscribers() < 0)
            user.setCountOfSubscribers(0L);
    }

    public User getByEmail(String eMail) {
        return userRepository.findByEmail(eMail).orElseThrow(() ->
                new NotFoundException("User not found with eMail " + eMail));
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////

    public List<User> getAll(Integer page, Integer itemsPerPage) {
        return userRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
    }

    public User getById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("User not found with id " + userId));
    }

    @Transactional
    public User create(String firstName, String lastName, String eMail, String password, LocalDate birthDate) {
        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(eMail);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setBirthDate(birthDate);
        return save(newUser);
    }

    @Transactional
    public User save(User newUser) {
        return userRepository.save(newUser);
    }

    @Transactional
    public User update(Long id, String firstName, String lastName, String eMail, String password, LocalDate birthDate, String currentEmail) {
        User userToBeUpdated = getById(id);
        if (!(userToBeUpdated.getEmail().equals(currentEmail)))
            throw new AuthorizationException("Forbidden action with current credentials");
        userToBeUpdated.setFirstName(firstName);
        userToBeUpdated.setLastName(lastName);
        userToBeUpdated.setEmail(eMail);
        userToBeUpdated.setPassword(passwordEncoder.encode(password));
        userToBeUpdated.setBirthDate(birthDate);
        return save(userToBeUpdated);
    }

    @Transactional
    public void delete(Long userId) {
        userRepository.delete(getById(userId));
    }

}
