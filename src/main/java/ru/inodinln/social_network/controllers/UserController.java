package ru.inodinln.social_network.controllers;

import org.springframework.web.bind.annotation.*;
import ru.inodinln.social_network.dto.UserCreationDTO;
import ru.inodinln.social_network.dto.UserUpdateDTO;
import ru.inodinln.social_network.dto.UserViewDTO;
import ru.inodinln.social_network.facades.UserFacade;
import ru.inodinln.social_network.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/API/v1/users")
public class UserController {

    private final UserFacade userFacade;
    private final UserService userService;

    public UserController (UserFacade userFacade, UserService userService) {
        this.userFacade = userFacade;
        this.userService = userService;
    }

    //get current users subscribees list:
    @GetMapping("/subscribees/{userId}")
    public List<UserViewDTO> getSubscribeesOfUser(@PathVariable("userId") Long userId) { //responseEntity
        return userFacade.getSubscribeesOfUser(userId);
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////
    @GetMapping
    public List<UserViewDTO> getAll(){ //responseEntity
       return userFacade.findAll();
    }

    @GetMapping("/{userId}")
    public UserViewDTO getById(@PathVariable("userId") Long userId) { //responseEntity
        return userFacade.findById(userId);
    }

    @PostMapping("/create")
    public void create(@RequestBody UserCreationDTO newUserDTO) {
        userFacade.save(newUserDTO);
    }

    @PostMapping("/update/{userId}")
    public void update(@PathVariable("userId") Long userId, @RequestBody UserUpdateDTO updateUserDTO) {
        userFacade.update(userId ,updateUserDTO);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable("userId") Long userId) {
        userService.delete(userId);
    }

    @PostMapping("/setRole/{userId}")
    public void setRole(@PathVariable("userId") Long userId, @RequestParam("role") int role) {
        userService.setRole(userId ,role);
    }
}
