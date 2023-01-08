package ru.inodinln.social_network.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.inodinln.social_network.dto.likesDTO.LikeCreatingDTO;
import ru.inodinln.social_network.dto.likesDTO.LikeViewDTO;
import ru.inodinln.social_network.facades.LikeFacade;

import java.util.List;

@RestController
@RequestMapping("/API/v1/likes")
public class LikeController {

    private final LikeFacade likeFacade;

    public LikeController(LikeFacade likeFacade) {
        this.likeFacade = likeFacade;
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////
    @GetMapping
    public ResponseEntity<List<LikeViewDTO>> getAll
    (@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
     @RequestParam(name = "itemsPerPage", required = false, defaultValue = "10") Integer itemsPerPage) {
        return new ResponseEntity<>(likeFacade.getAll(page, itemsPerPage), HttpStatus.OK);
    }

    @GetMapping("/{likeId}")
    public ResponseEntity<LikeViewDTO> getById(@PathVariable("likeId") Long likeId) {
        return new ResponseEntity<>(likeFacade.getById(likeId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LikeViewDTO> create(@RequestBody LikeCreatingDTO likeDTO) {
        return new ResponseEntity<>(likeFacade.create(likeDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{likeId}")
    public ResponseEntity<Void> delete(@PathVariable("likeId") Long likeId) {
        likeFacade.delete(likeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
