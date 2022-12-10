package ru.inodinln.social_network.facades;

import org.springframework.stereotype.Service;
import ru.inodinln.social_network.dto.dialogsDTO.DialogCreatingDTO;
import ru.inodinln.social_network.dto.dialogsDTO.DialogReducedViewDTO;
import ru.inodinln.social_network.dto.dialogsDTO.DialogViewDTO;
import ru.inodinln.social_network.exceptions.ValidationService;
import ru.inodinln.social_network.services.DialogService;
import ru.inodinln.social_network.utils.MapperService;

import java.util.List;

@Service
public class DialogFacade {

    private final DialogService dialogService;

    public DialogFacade(DialogService dialogService){
        this.dialogService = dialogService;
    }


    ////////////////////////////Business methods section///////////////////////////////////////
    public List<DialogReducedViewDTO> getUsersDialogs(Long userId, Integer page, Integer itemsPerPage){
        return MapperService.mapperForCollectionOfDialogReducedViewDTO(dialogService.getUsersDialogs(userId, page, itemsPerPage));
    }

    ////////////////////////////Admin methods section///////////////////////////////////////
    public List<DialogViewDTO> getDialogsByUserId(Long userId, Integer page, Integer itemsPerPage){
        return MapperService.mapperForCollectionOfDialogViewDTO(dialogService.getDialogsByUserId(userId, page, itemsPerPage));
   }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////

   public List<DialogViewDTO> getAll(Integer page, Integer itemsPerPage){
        return MapperService.mapperForCollectionOfDialogViewDTO(dialogService.getAll(page, itemsPerPage));
   }

    public DialogViewDTO getById(Long dialogId){
        return MapperService.mapperForSingleDialogViewDTO(dialogService.getById(dialogId));
    }

    public DialogViewDTO create(DialogCreatingDTO creatingDto){

        ValidationService.dialogCreatingDtoValidation(creatingDto);

        return MapperService.mapperForSingleDialogViewDTO(dialogService
                .create(creatingDto.getAuthorId(), creatingDto.getCompanionId()));
   }

   public void delete(Long dialogId){
       dialogService.delete(dialogId);
   }

}
