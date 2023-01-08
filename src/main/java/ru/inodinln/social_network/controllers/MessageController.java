package ru.inodinln.social_network.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.inodinln.social_network.dto.messagesDTO.MessageCreatingDTO;
import ru.inodinln.social_network.dto.messagesDTO.MessageViewDTO;
import ru.inodinln.social_network.facades.MessageFacade;

import java.util.List;

@RestController
@RequestMapping("/API/v1/messages")
public class MessageController {

    private final MessageFacade messageFacade;

    public MessageController(MessageFacade messageFacade) {
        this.messageFacade = messageFacade;
    }


    ////////////////////////////Business methods section///////////////////////////////////////

    @GetMapping("/userSent/{userId}")
    public ResponseEntity<List<MessageViewDTO>> getUserSentMessages
            (@PathVariable("userId") Long userId,
             @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
             @RequestParam(name = "itemsPerPage", required = false, defaultValue = "10") Integer itemsPerPage) {
        return new ResponseEntity<>(messageFacade.getUserSentMessages(userId, page, itemsPerPage), HttpStatus.OK);
    }

    @GetMapping("/userReceived/{userId}")
    public ResponseEntity<List<MessageViewDTO>> getUserReceivedMessages
            (@PathVariable("userId") Long userId,
             @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
             @RequestParam(name = "itemsPerPage", required = false, defaultValue = "10") Integer itemsPerPage) {
        return new ResponseEntity<>(messageFacade.getUserReceivedMessages(userId, page, itemsPerPage), HttpStatus.OK);
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////
    @GetMapping
    public ResponseEntity<List<MessageViewDTO>> getAll
    (@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
     @RequestParam(name = "itemsPerPage", required = false, defaultValue = "10") Integer itemsPerPage) {
        return new ResponseEntity<>(messageFacade.getAll(page, itemsPerPage), HttpStatus.OK);
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<MessageViewDTO> getById(@PathVariable("messageId") Long messageId) {
        return new ResponseEntity<>(messageFacade.getById(messageId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MessageViewDTO> create(@RequestBody MessageCreatingDTO messageDTO, Authentication authentication) {

        String s = ((UserDetails) authentication.getPrincipal()).getUsername();
        System.out.println(s);
        return new ResponseEntity<>(messageFacade.create(messageDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> delete(@PathVariable("messageId") Long messageId) {
        messageFacade.delete(messageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
