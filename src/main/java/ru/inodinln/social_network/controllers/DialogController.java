package ru.inodinln.social_network.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.inodinln.social_network.dto.dialogsDTO.DialogCreatingDTO;
import ru.inodinln.social_network.dto.dialogsDTO.DialogReducedViewDTO;
import ru.inodinln.social_network.dto.dialogsDTO.DialogViewDTO;
import ru.inodinln.social_network.facades.DialogFacade;

import java.util.List;

@RestController
@RequestMapping("/API/v1/dialogs")
public class DialogController {

    private final DialogFacade dialogFacade;

    public DialogController(DialogFacade dialogFacade) {
        this.dialogFacade = dialogFacade;
    }


    ////////////////////////////Business methods section///////////////////////////////////////

    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<DialogReducedViewDTO>> getUsersDialogs
            (@PathVariable("userId") Long userId,
             @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
             @RequestParam(name = "itemsPerPage", required = false, defaultValue = "10") Integer itemsPerPage) {
        return new ResponseEntity<>(dialogFacade.getUsersDialogs(userId, page, itemsPerPage), HttpStatus.OK);
    }

    ////////////////////////////Admin methods section///////////////////////////////////////

    @GetMapping("/admin/userId/{userId}")
    public ResponseEntity<List<DialogViewDTO>> getDialogsByUserId
            (@PathVariable("userId") Long userId,
             @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
             @RequestParam(name = "itemsPerPage", required = false, defaultValue = "10") Integer itemsPerPage) {
        return new ResponseEntity<>(dialogFacade.getDialogsByUserId(userId, page, itemsPerPage), HttpStatus.OK);
    }


    ////////////////////////////Basic CRUD methods section///////////////////////////////////////
    @GetMapping
    public ResponseEntity<List<DialogViewDTO>> getAll
    (@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
     @RequestParam(name = "itemsPerPage", required = false, defaultValue = "10") Integer itemsPerPage) {
        return new ResponseEntity<>(dialogFacade.getAll(page, itemsPerPage), HttpStatus.OK);
    }

    @GetMapping("/{dialogId}")
    public ResponseEntity<DialogViewDTO> getById(@PathVariable("dialogId") Long dialogId) {
        return new ResponseEntity<>(dialogFacade.getById(dialogId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DialogViewDTO> create(@RequestBody DialogCreatingDTO dialogDTO) {
        return new ResponseEntity<>(dialogFacade.create(dialogDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{dialogId}")
    public ResponseEntity<Void> delete(@PathVariable("dialogId") Long dialogId) {
        dialogFacade.delete(dialogId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
