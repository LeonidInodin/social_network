package ru.inodinln.social_network.controllers;

import org.springframework.web.bind.annotation.*;
import ru.inodinln.social_network.dto.likesDTO.LikeCreationDTO;
import ru.inodinln.social_network.dto.likesDTO.LikeViewDTO;
import ru.inodinln.social_network.facades.LikeFacade;
import ru.inodinln.social_network.services.LikeService;

import java.util.List;

@RestController
@RequestMapping("/API/v1/likes")
public class LikeController {

        private final LikeFacade likeFacade;

        private final LikeService likeService;

        public LikeController(LikeService likeService, LikeFacade likeFacade) {
            this.likeService = likeService;
            this.likeFacade = likeFacade;
        }

        ////////////////////////////Basic CRUD methods section///////////////////////////////////////
        @GetMapping
        public List<LikeViewDTO> findAll(){ //responseEntity
            return likeFacade.findAll();
        }

        @GetMapping("/{likeId}")
        public LikeViewDTO findById(@PathVariable("likeId") Long likeId) {
            return likeFacade.findById(likeId);
        }

        @PostMapping
        public void create(@RequestBody LikeCreationDTO likeDTO) {
            likeFacade.save(likeDTO);
        }

        @DeleteMapping("/{likeId}")
        public void delete(@PathVariable("likeId") Long likeId) {
            likeService.delete(likeId);
        }

    }
