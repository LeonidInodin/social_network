package ru.inodinln.social_network.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.inodinln.social_network.dto.statisticsDTO.StatisticsRequestDTO;
import ru.inodinln.social_network.dto.statisticsDTO.StatisticsUserViewDTO;
import ru.inodinln.social_network.dto.usersDTO.UserUpdatingDTO;
import ru.inodinln.social_network.dto.usersDTO.UserViewDTO;
import ru.inodinln.social_network.facades.UserFacade;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/API/v1/users")
public class UserController {

    private final UserFacade userFacade;

    public UserController (UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    ////////////////////////////Business methods section///////////////////////////////////////

    @GetMapping("/membersOfConversation/{conversationId}")
    public ResponseEntity<List<UserViewDTO>> getMembersByConversationId
            (@PathVariable("conversationId") Long conversationId,
             @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
             @RequestParam(name = "itemsPerPage", required = false, defaultValue = "10") Integer itemsPerPage,
             Authentication authentication) {
        return new ResponseEntity<>(userFacade.getMembersByConversationId(conversationId, page, itemsPerPage,
                ((UserDetails) authentication.getPrincipal()).getUsername()), HttpStatus.OK);
    }

    @PostMapping("/setRole/{userId}")
    public ResponseEntity<Void> setRole
            (@PathVariable("userId") Long userId,
             @RequestParam(name = "role", required = false, defaultValue = "ROLE_ADMIN") String role) {
        userFacade.setRole(userId ,role);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    ////////////////////////////Statistics methods section///////////////////////////////////////

    @GetMapping("/statistics/mostActive")
    public ResponseEntity<List<StatisticsUserViewDTO>> getMostActiveUsers
            (@RequestBody @Valid StatisticsRequestDTO requestDto,
             @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
             @RequestParam(name = "itemsPerPage", required = false, defaultValue = "10") Integer itemsPerPage){
        return new ResponseEntity<>(userFacade.getMostActiveUsers(requestDto, page, itemsPerPage), HttpStatus.OK);
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////

    @GetMapping
    public ResponseEntity <List<UserViewDTO>> getAll
            (@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
             @RequestParam(name = "itemsPerPage", required = false, defaultValue = "10") Integer itemsPerPage){
       return new ResponseEntity<>(userFacade.getAll(page, itemsPerPage), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserViewDTO> getById(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(userFacade.getById(userId), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<UserViewDTO> update(@RequestBody @Valid UserUpdatingDTO userToBeUpdatedDTO,
                                              Authentication authentication) {
        return new ResponseEntity<> (userFacade.update(userToBeUpdatedDTO,
                ((UserDetails) authentication.getPrincipal()).getUsername()), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@PathVariable("userId") Long userId) {
        userFacade.delete(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
