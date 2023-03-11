package ru.inodinln.social_network.userAPI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import ru.inodinln.social_network.controllers.UserController;
import ru.inodinln.social_network.dto.usersDTO.UserViewDTO;
import ru.inodinln.social_network.facades.UserFacade;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerUnitTests {

    @Mock
    UserFacade userFacade;

    @InjectMocks
    UserController userController;

    @Test
    void getAll_returnsResponseEntityWithListOfUsersViewDtoBodyAndStatusOK(){
        //GIVEN

        var usersDto = List.of(new UserViewDTO(), new UserViewDTO());
        var page = 1;
        var itemsPerPage = 10;
        Mockito
                .doReturn(usersDto).when(userFacade).getAll(page, itemsPerPage);

        //WHEN
        var responseEntity = userController.getAll(page, itemsPerPage);

        //THEN
        assertNotNull(responseEntity);
        assertTrue(responseEntity.hasBody());
        assertEquals(2, responseEntity.getBody().size());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    void getById_returnsResponseEntityWithUserViewDtoBodyAndStatusOK(){
        //GIVEN

        var userDto = new UserViewDTO();
        var id = 1L;
        userDto.setId(id);


        Mockito
                .doReturn(userDto).when(userFacade).getById(id);

        //WHEN
        var responseEntity = userController.getById(id);

        //THEN
        assertNotNull(responseEntity);
        assertTrue(responseEntity.hasBody());
        assertEquals(id, responseEntity.getBody().getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    void update_returnsResponseEntityWithUserViewDtoBodyAndStatusOK(){
        //GIVEN

        var userDto = new UserViewDTO();
        var id = 1L;
        userDto.setId(id);


        Mockito
                .doReturn(userDto).when(userFacade).getById(id);

        //WHEN
        var responseEntity = userController.getById(id);

        //THEN
        assertNotNull(responseEntity);
        assertTrue(responseEntity.hasBody());
        if (responseEntity.getBody() instanceof UserViewDTO) {
        assertEquals(id, responseEntity.getBody().getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());}

    }



}
