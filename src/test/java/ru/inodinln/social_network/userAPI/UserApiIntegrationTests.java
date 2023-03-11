package ru.inodinln.social_network.userAPI;

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
import ru.inodinln.social_network.controllers.UserController;
import ru.inodinln.social_network.exceptions.businessException.NotFoundException;
import ru.inodinln.social_network.exceptions.securityException.AuthorizationException;
import ru.inodinln.social_network.exceptions.validationException.ValidationException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Sql("/data.sql")
@Transactional
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class UserApiIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JwtTestService jwtTestService;

    @Autowired
    UserController userController;

    @Test
    void getAll_userJustAuthenticated_returnsResponseEntityWithListOfUsersViewDtoBodyAndStatusOK() throws Exception {
        //GIVEN
        var eMail = "ivanov@mail.com";
        var requestBuilder = MockMvcRequestBuilders.get("/API/v1/users")
                .header("Authorization", "Bearer " + jwtTestService.getValidToken(eMail));

        //WHEN
        mockMvc.perform(requestBuilder)

                //THEN
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        MockMvcResultMatchers.jsonPath("$[0].id")
                                .isNotEmpty(),
                        MockMvcResultMatchers.jsonPath("$[0].regDate")
                                .isNotEmpty(),
                        MockMvcResultMatchers.jsonPath("$[1].id")
                                .isNotEmpty(),
                        MockMvcResultMatchers.jsonPath("$[1].regDate")
                                .isNotEmpty()
                );
    }

    @Test
    void getAll_userIsNotAuthenticated_returnsStatusForbidden() throws Exception {
        //GIVEN
        var requestBuilder = MockMvcRequestBuilders.get("/API/v1/users");

        //WHEN
        mockMvc.perform(requestBuilder)

                //THEN
                .andExpectAll(
                        status().isForbidden()
                );
    }

    @Test
    void getById_userWithIdExistsAndCurrentUserAuthenticated_returnsResponseEntityWithUserViewDtoBodyAndStatusOK() throws Exception {
        //GIVEN
        var eMail = "ivanov@mail.com";
        var id = 10L;
        var requestBuilder = MockMvcRequestBuilders.get("/API/v1/users/" + id)
                .header("Authorization", "Bearer " + jwtTestService.getValidToken(eMail));

        //WHEN
        mockMvc.perform(requestBuilder)

                //THEN
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        MockMvcResultMatchers.jsonPath("$.id")
                                .value(id)
                );
    }

    @Test
    void getById_UserWithIdDoesNotExistAndCurrentUserAuthenticated_returnsJsonWithNotFoundExceptionAndStatusNotFound() throws Exception {
        //GIVEN
        var eMail = "ivanov@mail.com";
        var id = 3000;
        var requestBuilder = MockMvcRequestBuilders.get("/API/v1/users/" + id)
                .header("Authorization", "Bearer " + jwtTestService.getValidToken(eMail));

        //WHEN
        mockMvc.perform(requestBuilder)

                //THEN
                .andExpectAll(
                        status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        result -> assertTrue(result.getResolvedException() instanceof NotFoundException)
                );
    }

    @Test
    void update_currentUserAuthenticated_returnsResponseEntityWithUserViewDtoBodyAndStatusOK() throws Exception {
        //GIVEN
        var eMail = "ivanov@mail.com";
        var requestBuilder = MockMvcRequestBuilders.put("/API/v1/users/update")
                .header("Authorization", "Bearer " + jwtTestService.getValidToken(eMail))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "id": 10,
                            "firstName": "UpdatedFirstName",
                            "lastName": "UpdatedLastName",
                            "email": "updatedIvanov@mail.com",
                            "password": "updated1234",
                            "birthDate": "1961-11-11"
                        }
                        """);


        //WHEN
        mockMvc.perform(requestBuilder)

                //THEN
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                    "id": 10,
                                    "firstName": "UpdatedFirstName",
                                    "lastName": "UpdatedLastName",
                                    "email": "updatedIvanov@mail.com",
                                    "regDate": "2022-12-12",
                                    "countOfSubscribers": 0,
                                    "birthDate": "1961-11-11"
                                }
                                """)
                );
    }

    @Test
    void update_currentUsersIdMismatchUpdatedUsersId_returnsJsonWithAuthorizationExceptionAndStatusForbidden() throws Exception {
        //GIVEN
        var eMail = "ivanov@mail.com";
        var requestBuilder = MockMvcRequestBuilders.put("/API/v1/users/update")
                .header("Authorization", "Bearer " + jwtTestService.getValidToken(eMail))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "id": 11,
                            "firstName": "UpdatedFirstName",
                            "lastName": "UpdatedLastName",
                            "email": "updatedIvanov@mail.com",
                            "password": "updated1234",
                            "birthDate": "1961-11-11"
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
    void update_currentUserEnteredInvalidFirstName_returnsJsonWithMethodArgumentNotValidExceptionAndStatusUnprocessableEntity() throws Exception {
        //GIVEN
        var eMail = "ivanov@mail.com";
        var requestBuilder = MockMvcRequestBuilders.put("/API/v1/users/update")
                .header("Authorization", "Bearer " + jwtTestService.getValidToken(eMail))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "id": 10,
                            "firstName": " ",
                            "lastName": "UpdatedLastName",
                            "email": "updatedIvanov@mail.com",
                            "password": "updated1234",
                            "birthDate": "1961-11-11"
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
    void update_currentUserEnteredInvalidBirthDate_returnsJsonWithValidationExceptionAndStatusUnprocessableEntity() throws Exception {
        //GIVEN
        var eMail = "ivanov@mail.com";
        var requestBuilder = MockMvcRequestBuilders.put("/API/v1/users/update")
                .header("Authorization", "Bearer " + jwtTestService.getValidToken(eMail))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "id": 10,
                            "firstName": "FirstName",
                            "lastName": "UpdatedLastName",
                            "email": "updatedIvanov@mail.com",
                            "password": "updated1234",
                            "birthDate": "1900-11-11"
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
    void delete_userToBeDeletedIsExists_returnsStatusOK() throws Exception {
        //GIVEN
        var eMail = "inodin@mail.com";
        var id = 11;
        var requestBuilder = MockMvcRequestBuilders.delete("/API/v1/users/" + id)
                .header("Authorization", "Bearer " + jwtTestService.getValidToken(eMail));

        //WHEN
        mockMvc.perform(requestBuilder)

                //THEN
                .andExpectAll(
                        status().isOk()
                );
    }

    @Test
    void delete_delete_userToBeDeletedDoesNotExists__returnsJsonWithNotFoundExceptionAndStatusNotFound() throws Exception {
        //GIVEN
        var eMail = "inodin@mail.com";
        var id = 3000;
        var requestBuilder = MockMvcRequestBuilders.delete("/API/v1/users/" + id)
                .header("Authorization", "Bearer " + jwtTestService.getValidToken(eMail));

        //WHEN
        mockMvc.perform(requestBuilder)

                //THEN
                .andExpectAll(
                        status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        result -> assertTrue(result.getResolvedException() instanceof NotFoundException)
                );
    }

    @Test
    void getMostActiveUsers_currentUserAuthenticatedAndAdmin_returnsJsonAndStatusOK() throws Exception {
        //GIVEN
        var eMail = "inodin@mail.com";
        var requestBuilder = MockMvcRequestBuilders.get("/API/v1/users/statistics/mostActive")
                .header("Authorization", "Bearer " + jwtTestService.getValidToken(eMail))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "startOfPeriod": "2022-12-14",
                            "endOfPeriod": "2022-12-17"
                        }
                        """);

        //WHEN
        mockMvc.perform(requestBuilder)

                //THEN
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                [
                                {
                                         "user": {
                                             "id": 10,
                                             "firstName": "Ivan",
                                             "lastName": "Ivanov"
                                         },
                                         "value": 3
                                     },
                                     {
                                         "user": {
                                             "id": 11,
                                             "firstName": "Simon",
                                             "lastName": "Semenov"
                                         },
                                         "value": 1
                                     }
                                ]
                                """)
                );
    }

}
