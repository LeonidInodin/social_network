package ru.inodinln.social_network.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inodinln.social_network.entities.Dialog;
import ru.inodinln.social_network.entities.User;
import ru.inodinln.social_network.exceptions.businessException.BusinessException;
import ru.inodinln.social_network.exceptions.businessException.NotFoundException;
import ru.inodinln.social_network.repositories.DialogRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DialogService {

    private final UserService userService;

    private final DialogRepository dialogRepository;

    public DialogService(DialogRepository dialogRepository, UserService userService) {
        this.dialogRepository = dialogRepository;
        this.userService = userService;
    }


    ////////////////////////////Business methods section///////////////////////////////////////
    public List<Dialog> getUsersDialogs(Long userId, Integer page, Integer itemsPerPage){
        User user = userService.getById(userId);
        return dialogRepository.findDialogsByAuthorAndCompanion(user, user, PageRequest.of(page, itemsPerPage))
                .stream().peek((e) -> {
                    if (e.getAuthor().equals(user))
                        e.setAuthor(null);
                    else e.setCompanion(null);
                }).toList();
    }

    ////////////////////////////Admin methods section///////////////////////////////////////
    public List<Dialog> getDialogsByUserId(Long userId, Integer page, Integer itemsPerPage){
        User user = userService.getById(userId);
       return dialogRepository.findDialogsByAuthorAndCompanion(user, user, PageRequest.of(page, itemsPerPage));
    }

    ////////////////////////////Service methods section///////////////////////////////////////

    public void isDialogContainsUser(Long dialogId, User user){
        if (dialogRepository.findDialogByIdAndAuthor(dialogId, user) == null &&
                dialogRepository.findDialogByIdAndCompanion(dialogId, user) == null)
            throw new BusinessException("User with id " + user.getId() + " is not a member of dialog with id " + dialogId);

    }


    ////////////////////////////Basic CRUD methods section///////////////////////////////////////

    public List<Dialog> getAll(Integer page, Integer itemsPerPage) {
        return dialogRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
    }

    public Dialog getById(Long dialogId) {
        return dialogRepository.findById(dialogId).orElseThrow(() ->
                new NotFoundException("Dialog not found with id " + dialogId));
    }

    @Transactional
    public Dialog create(Long authorId, Long companionId) {
        User author = userService.getById(authorId);
        User companion = userService.getById(companionId);
        Dialog newDialog = new Dialog();
        if (dialogRepository.findDialogByAuthorAndCompanion(author, companion) != null
        || dialogRepository.findDialogByAuthorAndCompanion(companion, author) != null)
            throw new BusinessException("Dialog with these users already exists");
        newDialog.setAuthor(author);
        newDialog.setCompanion(companion);
        return save(newDialog);

    }

    @Transactional
    public Dialog save(Dialog newDialog){
        return dialogRepository.save(newDialog);
    }

    @Transactional
    public void delete(Long dialogId) {
        dialogRepository.delete(getById(dialogId));
    }

}
