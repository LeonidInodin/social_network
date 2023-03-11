package ru.inodinln.social_network.securityTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.inodinln.social_network.JwtTestService;
import ru.inodinln.social_network.exceptions.securityException.AuthorizationException;
import ru.inodinln.social_network.exceptions.validationException.ValidationException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Sql("/data.sql")
@Transactional
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class AuthenticationControllerIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JwtTestService jwtTestService;

    @Test
    void create_newUserIsValid_returnsResponseEntityWithStatusOK() throws Exception {
        //GIVEN
        var requestBuilder = MockMvcRequestBuilders.post("/API/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "firstName": "Anton",
                            "lastName": "Chehov",
                            "email": "chehovAP@email.com",
                            "password": "123",
                            "birthDate": "1960-12-12"
                        }
                        """);

        //WHEN
        mockMvc.perform(requestBuilder)

                //THEN
                .andExpectAll(
                        status().isCreated()
                );
    }

    @Test
    void create_newUsersEmailIsNotUnique_returnsJsonWithValidationExceptionWithStatusUnprocessableEntity() throws Exception {
        //GIVEN
        var requestBuilder = MockMvcRequestBuilders.post("/API/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "firstName": "Anton",
                            "lastName": "Chehov",
                            "email": "ivanov@mail.com",
                            "password": "123",
                            "birthDate": "1960-12-12"
                        }
                        """);

        //WHEN
        mockMvc.perform(requestBuilder)

                //THEN
                .andExpectAll(
                        status().isUnprocessableEntity(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        result -> assertTrue(result.getResolvedException() instanceof ValidationException)
                );
    }

    @Test
    void create_newUsersFirstNameIsEmpty_returnsJsonWithValidationExceptionWithStatusUnprocessableEntity() throws Exception {
        //GIVEN
        var requestBuilder = MockMvcRequestBuilders.post("/API/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "firstName": "   ",
                            "lastName": "Chehov",
                            "email": "ivanov@mail.com",
                            "password": "123",
                            "birthDate": "1960-12-12"
                        }
                        """);

        //WHEN
        mockMvc.perform(requestBuilder)

                //THEN
                .andExpectAll(
                        status().isUnprocessableEntity(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException)
                );
    }

    @Test
    void create_userJustAuthenticated_returnsJsonWithAuthorizationExceptionWithStatusForbidden() throws Exception {
        //GIVEN
        var eMail = "ivanov@mail.com";
        var requestBuilder = MockMvcRequestBuilders.post("/API/v1/auth/register")
                .header("Authorization", "Bearer " + jwtTestService.getValidToken(eMail))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "firstName": "Anton",
                            "lastName": "Chehov",
                            "email": "ivanov@mail.com",
                            "password": "123",
                            "birthDate": "1960-12-12"
                        }
                        """);

        //WHEN
        mockMvc.perform(requestBuilder)

                //THEN
                .andExpectAll(
                        status().isForbidden(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        result -> assertTrue(result.getResolvedException() instanceof AuthorizationException)
                );
    }

    @Test
    void authenticate_enterValidCredentials_returnsJsonWithUserIdAndJwt() throws Exception {
        //GIVEN
        var requestBuilder = MockMvcRequestBuilders.get("/API/v1/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email" : "ivanov@mail.com",
                            "password" : "123"
                        }
                        """);

        //WHEN
        mockMvc.perform(requestBuilder)

                //THEN
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        MockMvcResultMatchers.jsonPath("$.id")
                                .value(10),
                        MockMvcResultMatchers.jsonPath("$.jwt")
                                .isNotEmpty()
                );
    }

    @Test
    void authenticate_enterValidCredentialsByAuthenticatedUser_returnsJsonWithAuthenticationExceptionStatusForbidden() throws Exception {
        //GIVEN
        var eMail = "ivanov@mail.com";
        var requestBuilder = MockMvcRequestBuilders.get("/API/v1/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + jwtTestService.getValidToken(eMail))
                .content("""
                        {
                            "email" : "ivanov@mail.com",
                            "password" : "123"
                        }
                        """);

        //WHEN
        mockMvc.perform(requestBuilder)

                //THEN
                .andExpectAll(
                        status().isForbidden(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        result -> assertTrue(result.getResolvedException() instanceof AuthorizationException)
                );
    }

    @Test
    void authenticate_enterInvalidCredentials_returnsJsonWithAuthorizationExceptionStatusForbidden() throws Exception {
        //GIVEN
        var requestBuilder = MockMvcRequestBuilders.get("/API/v1/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email" : "123@mail.com",
                            "password" : "123"
                        }
                        """);

        //WHEN
        mockMvc.perform(requestBuilder)

                //THEN
                .andExpectAll(
                        status().isForbidden(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        result -> assertTrue(result.getResolvedException() instanceof AuthorizationException)
                );
    }


}
