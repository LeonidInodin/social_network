package ru.inodinln.social_network.facades;


import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ru.inodinln.social_network.dto.usersDTO.UserCreationDTO;
import ru.inodinln.social_network.dto.usersDTO.UserUpdateDTO;
import ru.inodinln.social_network.dto.usersDTO.UserViewDTO;
import ru.inodinln.social_network.entities.User;
import ru.inodinln.social_network.exceptions.ValidationService;
import ru.inodinln.social_network.services.UserService;
import ru.inodinln.social_network.utils.UtilAppService;

import java.util.List;

@Service
public class UserFacade {

    private final UserService userService;

    public UserFacade (UserService userService) {
        this.userService = userService;
    }

    //get current users subscribees list:
    public List<UserViewDTO> getSubscribeesOfUser(Long userId){
        return (List<UserViewDTO>) UtilAppService.convertCollectionsEntityAndDTO(userService.getSubscribeesOfUser(userId), UserViewDTO.class);
    }


    ////////////////////////////Basic CRUD methods section///////////////////////////////////////
    public List<UserViewDTO> findAll() {
        List<User> listUser = userService.findAll();
        return (List<UserViewDTO>) UtilAppService.convertCollectionsEntityAndDTO(listUser, UserViewDTO.class);
    }

    public UserViewDTO findById(Long userId) {
        UserViewDTO dto = new UserViewDTO();
        BeanUtils.copyProperties(userService.findById(userId), dto);
        return dto;
    }

    public UserViewDTO save(UserCreationDTO newUserDTO) {

        newUserDTO.setFirstName(newUserDTO.getFirstName().trim());
        newUserDTO.setLastName(newUserDTO.getLastName().trim());

        ValidationService.UserCreationDtoValidation(newUserDTO);

        UserViewDTO userDTO = new UserViewDTO();
        User newUser = new User();
        BeanUtils.copyProperties(newUserDTO, newUser);
        BeanUtils.copyProperties(userService.save(newUser), userDTO);
        return userDTO;

    }

    public UserViewDTO update(Long userId, UserUpdateDTO updateUserDTO) {

        updateUserDTO.setFirstName(updateUserDTO.getFirstName().trim());
        updateUserDTO.setLastName(updateUserDTO.getLastName().trim());

        ValidationService.UserUpdateDtoValidation(updateUserDTO);

        User userToBeUpdated = userService.findById(userId);
        UserViewDTO userDTO = new UserViewDTO();
        BeanUtils.copyProperties(updateUserDTO, userToBeUpdated);
        BeanUtils.copyProperties(userService.update(userToBeUpdated), userDTO);
        return userDTO;
    }

}
