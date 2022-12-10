package ru.inodinln.social_network.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.inodinln.social_network.dto.commentsDTO.CommentCreatingDTO;
import ru.inodinln.social_network.dto.commentsDTO.CommentUpdatingDTO;
import ru.inodinln.social_network.dto.commentsDTO.CommentViewDTO;
import ru.inodinln.social_network.dto.commentsDTO.CommentsTreeViewDTO;
import ru.inodinln.social_network.facades.CommentFacade;

import java.util.List;

@RestController
@RequestMapping("/API/v1/comments")
public class CommentsController {

    private final CommentFacade commentFacade;

    public CommentsController(CommentFacade commentFacade) {
        this.commentFacade = commentFacade;
    }

    ////////////////////////////Business methods section///////////////////////////////////////

    @GetMapping("commentsTree/post/{postId}")
    public ResponseEntity<List<CommentsTreeViewDTO>> getCommentsTreeByPostId
            (@PathVariable("postId") Long postId,
             @RequestParam(required = false, defaultValue = "0") Integer page,
             @RequestParam(required = false, defaultValue = "10") Integer itemsPerPage) {
        return new ResponseEntity<>(commentFacade.getCommentsTreeByPostId(postId, page, itemsPerPage), HttpStatus.OK);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentViewDTO>> getCommentsByPostId
            (@PathVariable("postId") Long postId,
             @RequestParam(required = false, defaultValue = "0") Integer page,
             @RequestParam(required = false, defaultValue = "10") Integer itemsPerPage) {
        return new ResponseEntity<>(commentFacade.getCommentsByPostId(postId, page, itemsPerPage), HttpStatus.OK);
    }


    ////////////////////////////Basic CRUD methods section///////////////////////////////////////
    @GetMapping
    public ResponseEntity<List<CommentViewDTO>> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer itemsPerPage) {
        return new ResponseEntity<>(commentFacade.getAll(page, itemsPerPage), HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentViewDTO> getById(@PathVariable("commentId") Long commentId) {
        return new ResponseEntity<>(commentFacade.getById(commentId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommentViewDTO> create(@RequestBody CommentCreatingDTO commentDTO) {
        return new ResponseEntity<>(commentFacade.create(commentDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<CommentViewDTO> update(@RequestBody CommentUpdatingDTO updateCommentDTO) {
        return new ResponseEntity<>(commentFacade.update(updateCommentDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable("commentId") Long commentId) {
        commentFacade.delete(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
