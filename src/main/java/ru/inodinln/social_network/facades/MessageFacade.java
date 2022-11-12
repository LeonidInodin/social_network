package ru.inodinln.social_network.facades;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ru.inodinln.social_network.dto.messagesDTO.MessageCreationDTO;
import ru.inodinln.social_network.dto.messagesDTO.MessageViewDTO;
import ru.inodinln.social_network.entities.Message;
import ru.inodinln.social_network.services.MessageService;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageFacade {

    private final MessageService messageService;

    public MessageFacade(MessageService messageService){
        this.messageService = messageService;
    }

    public List<MessageViewDTO> findUserSendedMessages(Long userId) {
        return packListDTO(messageService.findUserSendedMessages(userId));
    }

    public List<MessageViewDTO> findUserReceivedMessages(Long userId) {
        return packListDTO(messageService.findUserReceivedMessages(userId));
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////

    public List<MessageViewDTO> findAll(){
        return packListDTO(messageService.findAll());
    }

    public MessageViewDTO findById(Long messageId){
        return packDTO(messageService.findById(messageId));
    }

    public void save(MessageCreationDTO messageDTO){
        messageService.save(messageDTO.getFrom(), messageDTO.getTo(), messageDTO.getText());
    }

    ////////////////////////////Service methods section///////////////////////////////////////

    public List<MessageViewDTO> packListDTO(List<Message> listOfMsg){
        List<MessageViewDTO> listOfDTO = new ArrayList<>(listOfMsg.size());
        for (Message msg : listOfMsg) {

            MessageViewDTO dto = new MessageViewDTO();
            BeanUtils.copyProperties(msg, dto);
            dto.setFrom(msg.getFrom().getId());
            dto.setTo(msg.getTo().getId());
            listOfDTO.add(dto);
        }
        return listOfDTO;
    }

    public MessageViewDTO packDTO(Message msg){
        MessageViewDTO DTO = new MessageViewDTO();
        BeanUtils.copyProperties(msg, DTO);
        DTO.setFrom(msg.getFrom().getId());
        DTO.setTo(msg.getTo().getId());
        return DTO;
    }
}
