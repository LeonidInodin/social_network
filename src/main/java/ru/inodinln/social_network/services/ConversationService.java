package ru.inodinln.social_network.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inodinln.social_network.entities.Conversation;
import ru.inodinln.social_network.entities.User;
import ru.inodinln.social_network.exceptions.businessException.BusinessException;
import ru.inodinln.social_network.exceptions.businessException.NotFoundException;
import ru.inodinln.social_network.repositories.ConversationRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ConversationService {

    private final UserService userService;

    private final ConversationRepository conversationRepository;

    public ConversationService(ConversationRepository conversationRepository, UserService userService) {
        this.conversationRepository = conversationRepository;
        this.userService = userService;
    }

    ////////////////////////////Business methods section///////////////////////////////////////
    public List<Conversation> getConversationsByUserId(Long userId, Integer page, Integer itemsPerPage) {
        return conversationRepository.findConversationsByMembersContaining
                (userService.getById(userId), PageRequest.of(page, itemsPerPage));
    }

    @Transactional
    public Conversation addUserToConversation(Long userId, Long conversationId) {
        User user = userService.getById(userId);
        Conversation conversation = getById(conversationId);
        if (conversation.getMembers().contains(user))
            throw new BusinessException("User with id " + userId
                    + " is already member of conversation with id " + conversationId);
        if (conversation.getMembers().size() == 30)
            throw new BusinessException("Conversation with id " + conversationId
                    + " already contains 30 members. This value is maximum.");
        conversation.getMembers().add(user);
        return save(conversation);
    }

    @Transactional
    public Conversation removeUserFromConversation(Long userId, Long conversationId) {
        User user = userService.getById(userId);
        Conversation conversation = getById(conversationId);
        if (!(conversation.getMembers().contains(user)))
            throw new BusinessException("User with id " + userId
                    + " is not a member of conversation with id " + conversationId);
        if (conversation.getAuthorId().equals(userId))
            throw new BusinessException("User with id " + userId
                    + " is author of conversation with id " + conversationId + ". It can't be removed");
        conversation.getMembers().remove(user);
        return save(conversation);
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////

    public List<Conversation> getAll(Integer page, Integer itemsPerPage) {
        return conversationRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
    }

    public Conversation getById(Long conversationId) {
        return conversationRepository.findById(conversationId).orElseThrow(() ->
                new NotFoundException("Conversation not found with id " + conversationId));
    }

    @Transactional
    public Conversation create(Long authorId, String name) {
        Conversation newConversation = new Conversation();
        List<User> members = new ArrayList<>();
        members.add(userService.getById(authorId));
        newConversation.setMembers(members);
        newConversation.setName(name);
        newConversation.setAuthorId(authorId);
        return save(newConversation);
    }

    @Transactional
    public Conversation save(Conversation newConversation) {
        return conversationRepository.save(newConversation);
    }

    @Transactional
    public Conversation update(Long conversationId, String name) {
        Conversation conversationForUpdating = getById(conversationId);
        conversationForUpdating.setName(name);
        conversationForUpdating.setActualTimestamp(LocalDateTime.now());
        return save(conversationForUpdating);
    }

    @Transactional
    public void delete(Long conversationId) {
        conversationRepository.delete(getById(conversationId));
    }

}


