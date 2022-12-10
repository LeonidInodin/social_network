package ru.inodinln.social_network.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.inodinln.social_network.dto.conversationsDTO.ConversationCreatingDTO;
import ru.inodinln.social_network.dto.conversationsDTO.ConversationUpdatingDTO;
import ru.inodinln.social_network.dto.conversationsDTO.ConversationViewDTO;
import ru.inodinln.social_network.facades.ConversationFacade;

import java.util.List;

@RestController
@RequestMapping("/API/v1/conversations")
public class ConversationController {

    private final ConversationFacade conversationFacade;

    public ConversationController(ConversationFacade conversationFacade) {
        this.conversationFacade = conversationFacade;
    }

    ////////////////////////////Business methods section///////////////////////////////////////

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ConversationViewDTO>> getConversationsByUserId
            (@PathVariable("userId") Long userId,
             @RequestParam(required = false, defaultValue = "0") Integer page,
             @RequestParam(required = false, defaultValue = "10") Integer itemsPerPage) {
        return new ResponseEntity<>(conversationFacade
                .getConversationsByUserId(userId, page, itemsPerPage), HttpStatus.OK);
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity<ConversationViewDTO> addUserToConversation
            (@PathVariable("userId") Long userId,
             @RequestParam("conversationId") Long conversationId) {
        return new ResponseEntity<>(conversationFacade.addUserToConversation(userId, conversationId), HttpStatus.OK);
    }

    @PostMapping("/remove/{userId}")
    public ResponseEntity<ConversationViewDTO> removeUserFromConversation(@PathVariable("userId") Long userId,
                                                                          @RequestParam("conversationId") Long conversationId) {
        return new ResponseEntity<>(conversationFacade.removeUserFromConversation(userId, conversationId), HttpStatus.OK);
    }


    ////////////////////////////Basic CRUD methods section///////////////////////////////////////
    @GetMapping
    public ResponseEntity<List<ConversationViewDTO>> getAll
    (@RequestParam(required = false, defaultValue = "0") Integer page,
     @RequestParam(required = false, defaultValue = "10") Integer itemsPerPage) {
        return new ResponseEntity<>(conversationFacade.getAll(page, itemsPerPage), HttpStatus.OK);
    }

    @GetMapping("/{conversationId}")
    public ResponseEntity<ConversationViewDTO> getById(@PathVariable("conversationId") Long conversationId) {
        return new ResponseEntity<>(conversationFacade.getById(conversationId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ConversationViewDTO> create(@RequestBody ConversationCreatingDTO conversationDTO) {
        return new ResponseEntity<>(conversationFacade.create(conversationDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ConversationViewDTO> update(@RequestBody ConversationUpdatingDTO updateConversationDTO) {
        return new ResponseEntity<>(conversationFacade.update(updateConversationDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{conversationId}")
    public ResponseEntity<Void> delete(@PathVariable("conversationId") Long conversationId) {
        conversationFacade.delete(conversationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
