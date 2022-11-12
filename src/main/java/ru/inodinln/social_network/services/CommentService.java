package ru.inodinln.social_network.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inodinln.social_network.entities.Comment;
import ru.inodinln.social_network.exceptions.businessException.NotFoundException;
import ru.inodinln.social_network.repositories.CommentRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    private final UserService userService;

    private final PostService postService;

    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new NotFoundException("Comment not found with id " + commentId));
    }

    @Transactional
    public void save(Long postId, Long authorId, String text) {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setPost(postService.findById(postId));
        comment.setAuthor(userService.findById(authorId));
        commentRepository.save(comment);
    }

    @Transactional
    public void delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new NotFoundException("Message not found with id " + commentId));
        commentRepository.delete(comment);
    }
}
