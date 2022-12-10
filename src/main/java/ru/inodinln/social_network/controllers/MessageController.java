package ru.inodinln.social_network.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<MessageViewDTO>> getUserSentMessages(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(messageFacade.getUserSentMessages(userId), HttpStatus.OK);
    }

    @GetMapping("/userReceived/{userId}")
    public ResponseEntity<List<MessageViewDTO>> getUserReceivedMessages(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(messageFacade.getUserReceivedMessages(userId), HttpStatus.OK);
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////
    @GetMapping
    public ResponseEntity<List<MessageViewDTO>> getAll() {
        return new ResponseEntity<>(messageFacade.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<MessageViewDTO> getById(@PathVariable("messageId") Long messageId) {
        return new ResponseEntity<>(messageFacade.getById(messageId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MessageViewDTO> create(@RequestBody MessageCreatingDTO messageDTO) {
        return new ResponseEntity<>(messageFacade.create(messageDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> delete(@PathVariable("messageId") Long messageId) {
        messageFacade.delete(messageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
