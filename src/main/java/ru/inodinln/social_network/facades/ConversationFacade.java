package ru.inodinln.social_network.facades;

import org.springframework.stereotype.Service;
import ru.inodinln.social_network.dto.conversationsDTO.ConversationCreatingDTO;
import ru.inodinln.social_network.dto.conversationsDTO.ConversationUpdatingDTO;
import ru.inodinln.social_network.dto.conversationsDTO.ConversationViewDTO;
import ru.inodinln.social_network.exceptions.ValidationService;
import ru.inodinln.social_network.services.ConversationService;
import ru.inodinln.social_network.utils.MapperService;

import java.util.List;

@Service
public class ConversationFacade {

    private final ConversationService conversationService;

    public ConversationFacade(ConversationService conversationService){
        this.conversationService = conversationService;
    }

   public List<ConversationViewDTO> getConversationsByUserId(Long userId, Integer page, Integer itemsPerPage){
        return MapperService.mapperForCollectionOfConversationViewDTO
                (conversationService.getConversationsByUserId(userId, page, itemsPerPage));
    }

   public ConversationViewDTO addUserToConversation(Long userId, Long conversationId) {
       return MapperService.mapperForSingleConversationViewDTO(conversationService.addUserToConversation(userId, conversationId));
   }

   public ConversationViewDTO removeUserFromConversation(Long userId, Long conversationId) {
        return MapperService.mapperForSingleConversationViewDTO(conversationService.removeUserFromConversation(userId, conversationId));
   }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////

    public List<ConversationViewDTO> getAll(Integer page, Integer itemsPerPage){
        return MapperService.mapperForCollectionOfConversationViewDTO
                (conversationService.getAll(page, itemsPerPage));
    }

    public ConversationViewDTO getById(Long conversationId){
        return MapperService.mapperForSingleConversationViewDTO(conversationService.getById(conversationId));
    }

    public ConversationViewDTO create(ConversationCreatingDTO creatingDto){

        ValidationService.conversationCreatingDtoValidation(creatingDto);

        return MapperService.mapperForSingleConversationViewDTO(conversationService
                .create(creatingDto.getAuthorId(), creatingDto.getName()));
    }

    public ConversationViewDTO update(ConversationUpdatingDTO updatingDto){

        ValidationService.conversationUpdatingDtoValidation(updatingDto);

        return MapperService.mapperForSingleConversationViewDTO(conversationService
                .update(updatingDto.getId(), updatingDto.getName()));
    }

    public void delete(Long conversationId){
        conversationService.delete(conversationId);
    }

}
