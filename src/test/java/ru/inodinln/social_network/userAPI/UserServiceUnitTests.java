package ru.inodinln.social_network.userAPI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.inodinln.social_network.entities.User;
import ru.inodinln.social_network.exceptions.businessException.NotFoundException;
import ru.inodinln.social_network.exceptions.securityException.AuthorizationException;
import ru.inodinln.social_network.repositories.UserRepository;
import ru.inodinln.social_network.security.Roles;
import ru.inodinln.social_network.services.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTests {

    @Mock
    UserRepository userRepository;

    //@Mock
    //ConversationService conversationService;

    //@Mock
    //PostService postService;

    @Mock
    PasswordEncoder passwordEncoder;

    //@Mock
    //JwtService jwtService;

   // @Mock
    //JwtUserDetailsService userDetailsService;

    //@Mock
    //AuthenticationManager authManager;

    @InjectMocks
    UserService userService;

    @Test
    void getAll_returnsListOfAllUsers(){

        //given
        var users = List.of(new User(), new User());
        var count = 1;
        var itemsPerPage = 10;
        var page = new PageImpl<>(users);

        Mockito
                .doReturn(page).when(userRepository)
                .findAll(PageRequest.of(count, itemsPerPage));

        //when
        var usersUnderTest = userService.getAll(1, 10);

        //then
        assertNotNull(usersUnderTest);
        assertEquals(2, usersUnderTest.size());
        assertEquals(users, usersUnderTest);
    }

    @Test
    void getById_UserWithIdExists_returnsUserWithId(){

        //given
        var user = new User();
        var id = 1L;
        user.setId(id);

        Mockito
                .doReturn(Optional.of(user)).when(this.userRepository)
                .findById(id);

        //when
        var userUnderTest = userService.getById(id);

        //then
        assertEquals(id, userUnderTest.getId());
    }

    @Test
    void getById_UserWithIdDoesNotExist_throwsNotFoundException(){

        //given
        var id = 1L;

        Mockito
                .doReturn(Optional.empty()).when(this.userRepository)
                .findById(id);

        //then
        assertThrows(NotFoundException.class, () -> userService.getById(id));
    }

    @Test
    void save_returnsSavedUser(){

        //given
        var user = new User();
        var id = 1L;
        user.setId(id);

        Mockito
                .doReturn(user).when(this.userRepository)
                .save(user);

        //when
        var userUnderTest = userService.save(user);

        //then
        assertNotNull(userUnderTest);
        assertEquals(id, userUnderTest.getId());
        verify(this.userRepository).save(user);

    }

    @Test
    void update_currentUserIdEqualsUserToBeUpdatedId_returnsUpdatedUser(){

        //GIVEN

        //method's parameters
        var id = 1L;
        var firstName = "fNupdated";
        var lastName = "lNupdated";
        var eMail = "updated@mail.com";
        var password = "123";
        var birthDate = LocalDate.parse("1989-11-13");
        var currentEmail = "current@mail.com";

        //users
        var userToBeUpdated = new User(1L, "FN", "LN",
                "current@mail.com", "123", LocalDate.parse("2022-12-12"),
                LocalDate.parse("1989-12-12"), Roles.ROLE_USER, 0L, null);

        //mocked methods
        Mockito
                .doReturn(Optional.of(userToBeUpdated)).when(userRepository).findById(id);
        Mockito
                .doReturn(Optional.of(userToBeUpdated)).when(userRepository).findByEmail(currentEmail);
        Mockito
                .doReturn("123").when(passwordEncoder).encode(password);
        Mockito
                .doReturn(userToBeUpdated).when(userRepository).save(userToBeUpdated);

        //WHEN
        var userUnderTest = userService.update(id, firstName, lastName, eMail, password,
                birthDate, currentEmail);

        //THEN
        assertNotNull(userUnderTest);
        assertEquals(userToBeUpdated, userUnderTest);

    }

    @Test
    void update_currentUserIdIsNotEqualsUserToBeUpdatedId_throwsAuthorizationException(){

        //GIVEN

        //method's parameters
        var id = 1L;
        var firstName = "fNupdated";
        var lastName = "lNupdated";
        var eMail = "updated@mail.com";
        var password = "123";
        var birthDate = LocalDate.parse("1989-11-13");
        var currentEmail = "current@mail.com";

        //users
        var userToBeUpdated = new User(1L, "FN", "LN",
                "another@mail.com", "123", LocalDate.parse("2022-12-12"),
                LocalDate.parse("1989-12-12"), Roles.ROLE_USER, 0L, null);

        var userWithAnotherId = new User(2L, "FN", "LN",
                "current@mail.com", "123", LocalDate.parse("2022-12-12"),
                LocalDate.parse("1989-12-12"), Roles.ROLE_USER, 0L, null);


        //mocked methods
        Mockito
                .doReturn(Optional.of(userToBeUpdated)).when(userRepository).findById(id);
        Mockito
                .doReturn(Optional.of(userWithAnotherId)).when(userRepository).findByEmail(currentEmail);

        //THEN
        assertThrows(AuthorizationException.class, () -> userService.update(id, firstName, lastName,
                eMail, password, birthDate, currentEmail));

    }





}
