package ru.inodinln.social_network.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inodinln.social_network.entities.Conversation;
import ru.inodinln.social_network.entities.Dialog;
import ru.inodinln.social_network.entities.Message;
import ru.inodinln.social_network.entities.User;
import ru.inodinln.social_network.exceptions.businessException.BusinessException;
import ru.inodinln.social_network.exceptions.businessException.NotFoundException;
import ru.inodinln.social_network.repositories.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MessageService {

    private final MessageRepository messageRepository;

    private final UserService userService;

    private final ConversationService conversationService;

    private final DialogService dialogService;

    public MessageService(MessageRepository messageRepository, UserService userService,
                          ConversationService conversationService, DialogService dialogService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.conversationService = conversationService;
        this.dialogService = dialogService;
    }

    public List<Message> getUserSentMessages(Long userId, Integer page, Integer itemsPerPage) {
        return messageRepository.findBySenderIdIs(userId, PageRequest.of(page, itemsPerPage));
    }

    public List<Message> getUserReceivedMessages(Long userId, Integer page, Integer itemsPerPage) {
        return messageRepository.findByRecipientIdIs(userId, PageRequest.of(page, itemsPerPage));
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////
    public List<Message> getAll(Integer page, Integer itemsPerPage) {
        return messageRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
    }

    public Message getById(Long messageId) {
        return messageRepository.findById(messageId).orElseThrow(() ->
                new NotFoundException("Message not found with id " + messageId));
    }

    @Transactional //prop
    public Message create(String type, Long containerId, Long senderId, Long recipientId, String text) {

        Message newMessage = new Message();
        User sender = userService.getById(senderId);
        newMessage.setSender(sender);
        newMessage.setText(text);

        Conversation conversation;
        Dialog dialog;

        if (type.equals("Conversation")) {
            conversation = conversationService.getById(containerId);
            if (!(conversation.getMembers().contains(sender)))
                throw new BusinessException("User with id " + senderId
                        + "is not a member of conversation with id " +containerId);
            if (recipientId != null) {
                User recipient = userService.getById(recipientId);
                if (!(conversation.getMembers().contains(recipient)))
                    throw new BusinessException("User with id " + recipientId
                            + "is not a member of conversation with id " +containerId);
                newMessage.setRecipient(recipient);
            }
            else newMessage.setRecipient(null);

            newMessage.setConversation(conversation);
            newMessage.setDialog(null);
            newMessage = save(newMessage);
            conversation.setActualTimestamp(newMessage.getTimestamp());
        }
        else {
            dialog = dialogService.getById(containerId);
            User recipient = userService.getById(recipientId);
            dialogService.isDialogContainsUser(dialog.getId(), sender);
            dialogService.isDialogContainsUser(dialog.getId(), recipient);
            newMessage.setRecipient(recipient);
            newMessage.setConversation(null);
            newMessage.setDialog(dialog);
            newMessage = save(newMessage);
            dialog.setActualTimestamp(newMessage.getTimestamp());
        }

        return newMessage;
    }

    @Transactional
    public Message save(Message newMessage){
        return messageRepository.save(newMessage);
    }

    @Transactional
    public Message update(Long messageId, String text) {
        Message messageForUpdating = getById(messageId);
        messageForUpdating.setText(text);
        messageForUpdating.setTimestampOfUpdating(LocalDateTime.now());
        return save(messageForUpdating);
    }

    @Transactional
    public void delete(Long messageId) {
        messageRepository.delete(getById(messageId));
    }
}
