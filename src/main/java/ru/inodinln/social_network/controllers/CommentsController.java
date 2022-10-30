package ru.inodinln.social_network.controllers;

import org.springframework.web.bind.annotation.*;
import ru.inodinln.social_network.dto.CommentCreationDTO;
import ru.inodinln.social_network.dto.CommentViewDTO;
import ru.inodinln.social_network.facades.CommentFacade;
import ru.inodinln.social_network.services.CommentService;

import java.util.List;

@RestController
@RequestMapping("/API/v1/comments")
public class CommentsController {

    private final CommentFacade commentFacade;

    private final CommentService commentService;

    public CommentsController(CommentService commentService, CommentFacade commentFacade) {
        this.commentService = commentService;
        this.commentFacade = commentFacade;
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////
    @GetMapping
    public List<CommentViewDTO> findAll(){ //responseEntity
        return commentFacade.findAll();
    }

    @GetMapping("/{commentId}")
    public CommentViewDTO findById(@PathVariable("commentId") Long commentId) {
        return commentFacade.findById(commentId);
    }

    @PostMapping
    public void create(@RequestBody CommentCreationDTO commentDTO) {
        commentFacade.save(commentDTO);
    }

    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable("commentId") Long commentId) {
        commentService.delete(commentId);
    }
}
