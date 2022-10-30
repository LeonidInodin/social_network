package ru.inodinln.social_network.controllers;

import org.springframework.web.bind.annotation.*;
import ru.inodinln.social_network.dto.PostCreationDTO;
import ru.inodinln.social_network.dto.PostViewDTO;
import ru.inodinln.social_network.facades.PostFacade;
import ru.inodinln.social_network.services.PostService;

import java.util.List;

@RestController
@RequestMapping("/API/v1/posts")
public class PostController {

        private final PostFacade postFacade;

        private final PostService postService;

        public PostController (PostService postService, PostFacade postFacade) {
            this.postService = postService;
            this.postFacade = postFacade;
        }

        ////////////////////////////Basic CRUD methods section///////////////////////////////////////
        @GetMapping
        public List<PostViewDTO> findAll(){ //responseEntity
            return postFacade.findAll();
        }

        @GetMapping("/{postId}")
        public PostViewDTO findById(@PathVariable("postId") Long postId) {
            return postFacade.findById(postId);
        }

        @PostMapping
        public void create(@RequestBody PostCreationDTO postDTO) {
            postFacade.save(postDTO);
        }

        @DeleteMapping("/{postId}")
        public void delete(@PathVariable("postId") Long postId) {
            postService.delete(postId);
        }

    }
