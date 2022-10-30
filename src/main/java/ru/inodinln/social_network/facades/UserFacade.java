package ru.inodinln.social_network.facades;


import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ru.inodinln.social_network.dto.UserCreationDTO;
import ru.inodinln.social_network.dto.UserUpdateDTO;
import ru.inodinln.social_network.dto.UserViewDTO;
import ru.inodinln.social_network.entities.User;
import ru.inodinln.social_network.services.UserService;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserFacade {

    private final UserService userService;

    public UserFacade (UserService userService) {
        this.userService = userService;
    }

    //get current users subscribees list:
    public List<UserViewDTO> getSubscribeesOfUser(Long userId){
        return packListDTO(userService.getSubscribeesOfUser(userId));
    }


    ////////////////////////////Basic CRUD methods section///////////////////////////////////////
    public List<UserViewDTO> findAll() {
        List<User> listUser = userService.findAll();
        List<UserViewDTO> listOfDTO = new ArrayList<>(listUser.size());
        for (User user : listUser) {

            UserViewDTO DTO = new UserViewDTO();
            BeanUtils.copyProperties(user, DTO);
            listOfDTO.add(DTO);
        }
        return listOfDTO;
    }

    public UserViewDTO findById(Long userId) {
        User user = userService.findById(userId);
        UserViewDTO DTO = new UserViewDTO();
        BeanUtils.copyProperties(user, DTO);
        return DTO;
    }

    public void save(UserCreationDTO newUserDTO) {
        User newUser = new User();
        BeanUtils.copyProperties(newUserDTO, newUser);
        userService.save(newUser);
    }

    public void update(Long userId, UserUpdateDTO updateUserDTO) {
        User userToBeUpdated = userService.findById(userId);
        BeanUtils.copyProperties(updateUserDTO, userToBeUpdated);
        userService.update(userToBeUpdated);
    }

    ////////////////////////////Service methods section///////////////////////////////////////

    public List<UserViewDTO> packListDTO(List<User> listOfUsers){
        List<UserViewDTO> listOfDTO = new ArrayList<>(listOfUsers.size());
        for (User user : listOfUsers) {

            UserViewDTO dto = new UserViewDTO();
            BeanUtils.copyProperties(user, dto);
            listOfDTO.add(dto);
        }
        return listOfDTO;
    }

}
