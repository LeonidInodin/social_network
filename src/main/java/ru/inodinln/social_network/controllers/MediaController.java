package ru.inodinln.social_network.controllers;

import org.springframework.web.bind.annotation.*;
import ru.inodinln.social_network.dto.MediaCreationDTO;
import ru.inodinln.social_network.dto.MediaViewDTO;
import ru.inodinln.social_network.dto.MessageCreationDTO;
import ru.inodinln.social_network.dto.MessageViewDTO;
import ru.inodinln.social_network.facades.MediaFacade;
import ru.inodinln.social_network.facades.MessageFacade;
import ru.inodinln.social_network.services.MediaService;
import ru.inodinln.social_network.services.MessageService;

import java.util.List;

@RestController
@RequestMapping("/API/v1/media")
public class MediaController {

    private final MediaFacade mediaFacade;

    private final MediaService mediaService;

    public MediaController(MediaService mediaService, MediaFacade mediaFacade) {
        this.mediaService = mediaService;
        this.mediaFacade = mediaFacade;
    }



    ////////////////////////////Basic CRUD methods section///////////////////////////////////////
    @GetMapping
    public List<MediaViewDTO> findAll(){ //responseEntity
        return mediaFacade.findAll();
    }

    @GetMapping("/{mediaId}")
    public MediaViewDTO findById(@PathVariable("mediaId") Long mediaId) {
        return mediaFacade.findById(mediaId);
    }

    @PostMapping
    public void create(@RequestBody MediaCreationDTO mediaDTO) {
        mediaFacade.save(mediaDTO);
    }

   @DeleteMapping("/{mediaId}")
   public void delete(@PathVariable("mediaId") Long mediaId) {
       mediaService.delete(mediaId);
    }
}
