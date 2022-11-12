package ru.inodinln.social_network.controllers;

import org.springframework.web.bind.annotation.*;
import ru.inodinln.social_network.dto.messagesDTO.MessageCreationDTO;
import ru.inodinln.social_network.dto.messagesDTO.MessageViewDTO;
import ru.inodinln.social_network.facades.MessageFacade;
import ru.inodinln.social_network.services.MessageService;

import java.util.List;

@RestController
@RequestMapping("/API/v1/messages")
public class MessageController {

    private final MessageFacade messageFacade;

    private final MessageService messageService;

    public MessageController (MessageService messageService, MessageFacade messageFacade) {
        this.messageService = messageService;
        this.messageFacade = messageFacade;
    }

    @GetMapping("/userSended/{userId}")
    public List<MessageViewDTO> findUserSendedMessages(@PathVariable("userId") Long userId){ //responseEntity
        return messageFacade.findUserSendedMessages(userId);
    }

    @GetMapping("/userReceived/{userId}")
    public List<MessageViewDTO> findUserReceivedMessages(@PathVariable("userId") Long userId){
        return messageFacade.findUserReceivedMessages(userId);
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////
    @GetMapping
    public List<MessageViewDTO> findAll(){ //responseEntity
        return messageFacade.findAll();
    }

    @GetMapping("/{messageId}")
    public MessageViewDTO findById(@PathVariable("messageId") Long messageId) {
        return messageFacade.findById(messageId);
    }

    @PostMapping
    public void create(@RequestBody MessageCreationDTO messageDTO) {
        messageFacade.save(messageDTO);
    }

    @DeleteMapping("/{messageId}")
    public void delete(@PathVariable("messageId") Long messageId) {
        messageService.delete(messageId);
    }
}
