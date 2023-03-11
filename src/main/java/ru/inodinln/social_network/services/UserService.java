package ru.inodinln.social_network.services;


import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inodinln.social_network.entities.User;
import ru.inodinln.social_network.exceptions.businessException.NotFoundException;
import ru.inodinln.social_network.exceptions.securityException.AuthorizationException;
import ru.inodinln.social_network.exceptions.validationException.ValidationException;
import ru.inodinln.social_network.repositories.UserRepository;
import ru.inodinln.social_network.security.JwtService;
import ru.inodinln.social_network.security.JwtUserDetailsService;
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

    private final JwtService jwtService;

    private final JwtUserDetailsService userDetailsService;

    private final AuthenticationManager authManager;

    public UserService(UserRepository userRepository,
                       @Lazy ConversationService conversationService,
                       @Lazy PostService postService,
                       @Lazy PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       @Lazy JwtUserDetailsService userDetailsService,
                       AuthenticationManager authManager) {
        this.userRepository = userRepository;
        this.conversationService = conversationService;
        this.postService = postService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.authManager = authManager;
    }
    ////////////////////////////Business methods section///////////////////////////////////////

    public List<User> getMembersByConversationId(Long conversationId, Integer page, Integer itemsPerPage, String eMail) {
        List<User> resultList = userRepository.findUsersByConversationsContains
                (conversationService.getById(conversationId), PageRequest.of(page, itemsPerPage));
        if (resultList.stream().noneMatch(u -> u.equals(getByEmail(eMail))))
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

    public User getByEmailForAuthentication(String eMail) {
        return userRepository.findByEmail(eMail).orElseThrow(() ->
                new AuthorizationException("Access denied"));
    }

    public void getByEmailForRegistration(String eMail) {
        if (userRepository.findByEmail(eMail).isPresent())
            throw new ValidationException("Email " + eMail + " occupied");
    }

    ////////////////////////////Security methods section///////////////////////////////////////
    @Transactional
    public User create(String firstName,
                       String lastName,
                       String eMail,
                       String password,
                       LocalDate birthDate,
                       Authentication authentication) {
        if (authentication != null)
            throw new AuthorizationException("Forbidden for authenticated users");
        getByEmailForRegistration(eMail);
        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(eMail);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setBirthDate(birthDate);
        return save(newUser);
    }

    public Map.Entry<Long, String> authenticate(String password, String eMail, Authentication authentication) {
        if (authentication != null)
            throw new AuthorizationException("Forbidden for authenticated users");
        getByEmailForAuthentication(eMail);
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(eMail, password)
        );
        String jwt = jwtService.generateToken(userDetailsService.loadUserByUsername(eMail));
        return new java.util.AbstractMap.SimpleEntry<>(getByEmail(eMail).getId(), jwt);
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
    public User save(User newUser) {
        return userRepository.save(newUser);
    }

    @Transactional
    public User update(Long id,
                       String firstName,
                       String lastName,
                       String eMail,
                       String password,
                       LocalDate birthDate,
                       String currentEmail) {
        User userToBeUpdated = getById(id);
        if (!(userToBeUpdated.getId().equals(getByEmail(currentEmail).getId())))
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
