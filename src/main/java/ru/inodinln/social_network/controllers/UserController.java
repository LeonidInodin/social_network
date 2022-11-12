package ru.inodinln.social_network.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.inodinln.social_network.dto.usersDTO.UserCreationDTO;
import ru.inodinln.social_network.dto.usersDTO.UserUpdateDTO;
import ru.inodinln.social_network.dto.usersDTO.UserViewDTO;
import ru.inodinln.social_network.facades.UserFacade;
import ru.inodinln.social_network.services.UserService;

import javax.validation.Valid;
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
    public ResponseEntity<List<UserViewDTO>> getSubscribeesOfUser(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(userFacade.getSubscribeesOfUser(userId), HttpStatus.OK);
    }

    /**
     * Basic CRUD methods section
     */
    @GetMapping
    public ResponseEntity <List<UserViewDTO>> getAll(){
       return new ResponseEntity<>(userFacade.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserViewDTO> getById(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(userFacade.findById(userId), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<UserViewDTO> create(@RequestBody @Valid UserCreationDTO newUserDTO) {
        return new ResponseEntity<>(userFacade.save(newUserDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserViewDTO> update(@PathVariable("userId") Long userId, @RequestBody UserUpdateDTO updateUserDTO) {
        return new ResponseEntity<> (userFacade.update(userId ,updateUserDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("userId") Long userId) {
        userService.delete(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/setRole/{userId}")
    public ResponseEntity<HttpStatus> setRole(@PathVariable("userId") Long userId, @RequestParam("role") int role) {
        userService.setRole(userId ,role);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
