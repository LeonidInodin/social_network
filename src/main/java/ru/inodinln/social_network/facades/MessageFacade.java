package ru.inodinln.social_network.facades;

import org.springframework.stereotype.Service;
import ru.inodinln.social_network.dto.messagesDTO.MessageCreatingDTO;
import ru.inodinln.social_network.dto.messagesDTO.MessageUpdatingDTO;
import ru.inodinln.social_network.dto.messagesDTO.MessageViewDTO;
import ru.inodinln.social_network.exceptions.ValidationService;
import ru.inodinln.social_network.services.MessageService;
import ru.inodinln.social_network.utils.MapperService;

import java.util.List;

@Service
public class MessageFacade {

    private final MessageService messageService;

    public MessageFacade(MessageService messageService){
        this.messageService = messageService;
    }

    public List<MessageViewDTO> getUserSentMessages(Long userId) {
        return MapperService.mapperForCollectionOfMessageViewDTO(messageService.getUserSentMessages(userId));
    }

    public List<MessageViewDTO> getUserReceivedMessages(Long userId) {
        return MapperService.mapperForCollectionOfMessageViewDTO(messageService.getUserReceivedMessages(userId));
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////

    public List<MessageViewDTO> getAll(){
        return MapperService.mapperForCollectionOfMessageViewDTO(messageService.getAll());
    }

    public MessageViewDTO getById(Long messageId){
        return MapperService.mapperForSingleMessageViewDTO(messageService.getById(messageId));
    }

    public MessageViewDTO create(MessageCreatingDTO messageDTO){
        ValidationService.messageCreatingDtoValidation(messageDTO);
        return MapperService.mapperForSingleMessageViewDTO(messageService.create(messageDTO.getType(),
                messageDTO.getContainerId(), messageDTO.getSenderId(), messageDTO.getRecipientId(), messageDTO.getText()));
    }

    public MessageViewDTO update(MessageUpdatingDTO messageDTO){
        ValidationService.messageUpdatingDtoValidation(messageDTO);
        return MapperService.mapperForSingleMessageViewDTO(messageService.update(messageDTO.getId(), messageDTO.getText()));
    }

    public void delete(Long messageId){
        messageService.delete(messageId);
    }

}
