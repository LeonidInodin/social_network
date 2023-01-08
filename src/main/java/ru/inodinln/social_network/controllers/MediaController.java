package ru.inodinln.social_network.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.inodinln.social_network.dto.mediaDTO.MediaCreatingDTO;
import ru.inodinln.social_network.dto.mediaDTO.MediaViewDTO;
import ru.inodinln.social_network.facades.MediaFacade;

import java.util.List;

@RestController
@RequestMapping("/API/v1/media")
public class MediaController {

    private final MediaFacade mediaFacade;

    public MediaController(MediaFacade mediaFacade) {
        this.mediaFacade = mediaFacade;
    }



    ////////////////////////////Basic CRUD methods section///////////////////////////////////////
    @GetMapping
    public ResponseEntity<List<MediaViewDTO>> getAll
    (@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
     @RequestParam(name = "itemsPerPage", required = false, defaultValue = "10") Integer itemsPerPage){
        return new ResponseEntity<>(mediaFacade.getAll(page, itemsPerPage), HttpStatus.OK);
    }

    @GetMapping("/{mediaId}")
    public ResponseEntity<MediaViewDTO> getById(@PathVariable("mediaId") Long mediaId) {
        return new ResponseEntity<>(mediaFacade.getById(mediaId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MediaViewDTO> create(@RequestBody MediaCreatingDTO mediaDTO) {
        return new ResponseEntity<>(mediaFacade.create(mediaDTO), HttpStatus.CREATED);
    }

   @DeleteMapping("/{mediaId}")
   public ResponseEntity<Void> delete(@PathVariable("mediaId") Long mediaId) {
       mediaFacade.delete(mediaId);
       return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
