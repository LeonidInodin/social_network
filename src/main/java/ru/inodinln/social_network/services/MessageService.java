package ru.inodinln.social_network.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inodinln.social_network.entities.Message;
import ru.inodinln.social_network.exceptions.businessException.NotFoundException;
import ru.inodinln.social_network.repositories.MessageRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MessageService {

    private final MessageRepository messageRepository;

    private final UserService userService;

    public MessageService(MessageRepository messageRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    public List<Message> findUserSendedMessages(Long userId) {
        return messageRepository.findByFrom_IdIs(userId);
    }

    public List<Message> findUserReceivedMessages(Long userId) {
        return messageRepository.findByTo_IdIs(userId);
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public Message findById(Long messageId) {
        return messageRepository.findById(messageId).orElseThrow(() ->
                new NotFoundException("Message not found with id " + messageId));
    }

    @Transactional
    public void save(Long from, Long to, String text) {
        Message msg = new Message();
        msg.setText(text);
        msg.setFrom(userService.findById(from));
        msg.setTo(userService.findById(to));
        messageRepository.save(msg);
    }

    @Transactional
    public void delete(Long messageId) {
        Message msg = messageRepository.findById(messageId).orElseThrow(() ->
                new NotFoundException("Message not found with id " + messageId));
        messageRepository.delete(msg);
    }
}
