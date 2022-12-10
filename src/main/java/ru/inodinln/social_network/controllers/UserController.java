package ru.inodinln.social_network.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.inodinln.social_network.dto.statisticsDTO.StatisticsRequestDTO;
import ru.inodinln.social_network.dto.statisticsDTO.StatisticsUserViewDTO;
import ru.inodinln.social_network.dto.usersDTO.UserCreatingDTO;
import ru.inodinln.social_network.dto.usersDTO.UserUpdatingDTO;
import ru.inodinln.social_network.dto.usersDTO.UserViewDTO;
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

    ////////////////////////////Business methods section///////////////////////////////////////

    @GetMapping("/membersOfConversation/{conversationId}")
    public ResponseEntity<List<UserViewDTO>> getMembersByConversationId
            (@PathVariable("conversationId") Long conversationId,
             @RequestParam(required = false, defaultValue = "0") Integer page,
             @RequestParam(required = false, defaultValue = "10") Integer itemsPerPage) {
        return new ResponseEntity<>(userFacade.getMembersByConversationId(conversationId, page, itemsPerPage), HttpStatus.OK);
    }

    @PostMapping("/setRole/{userId}")
    public ResponseEntity<Void> setRole(@PathVariable("userId") Long userId, @RequestParam("role") Integer roleId) {
        userService.setRole(userId ,roleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    ////////////////////////////Statistics methods section///////////////////////////////////////

    @GetMapping("/statistics/mostActive")
    public ResponseEntity<List<StatisticsUserViewDTO>> get10mostActive(@RequestBody StatisticsRequestDTO requestDto){
        return new ResponseEntity<>(userFacade.get10mostActive(requestDto), HttpStatus.OK);
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////

    @GetMapping
    public ResponseEntity <List<UserViewDTO>> getAll
            (@RequestParam(required = false, defaultValue = "0") Integer page,
             @RequestParam(required = false, defaultValue = "10") Integer itemsPerPage){
       return new ResponseEntity<>(userFacade.getAll(page, itemsPerPage), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserViewDTO> getById(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(userFacade.getById(userId), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<UserViewDTO> create(@RequestBody UserCreatingDTO newUserDTO) {
        return new ResponseEntity<>(userFacade.create(newUserDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<UserViewDTO> update(@RequestBody UserUpdatingDTO userToBeUpdatedDTO) {
        return new ResponseEntity<> (userFacade.update(userToBeUpdatedDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@PathVariable("userId") Long userId) {
        userService.delete(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
