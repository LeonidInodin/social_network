package ru.inodinln.social_network.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inodinln.social_network.entities.Comment;
import ru.inodinln.social_network.entities.User;
import ru.inodinln.social_network.exceptions.businessException.NotFoundException;
import ru.inodinln.social_network.exceptions.securityException.AuthorizationException;
import ru.inodinln.social_network.repositories.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostService postService;

    private final UserService userService;

    public CommentService(CommentRepository commentRepository, PostService postService, UserService userService) {
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.userService = userService;
    }

    ////////////////////////////Business methods section///////////////////////////////////////

    public List<Comment> getCommentsTreeByPostId(Long postId, Integer page, Integer itemsPerPage) {
        List<Comment> list = commentRepository.findCommentsByPostAndLevel
                (postService.getById(postId), 1, PageRequest.of(page, itemsPerPage));
        if (list.isEmpty())
            throw new NotFoundException("Comments not found by post with id " + postId);
        return list;
    }

    public List<Comment> getCommentsByPostId(Long postId, Integer page, Integer itemsPerPage) {
        List<Comment> list = commentRepository.findCommentsByPost
                (postService.getById(postId), PageRequest.of(page, itemsPerPage));
        if (list.isEmpty())
            throw new NotFoundException("Comments not found by post with id " + postId);
        return list;
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////
    public List<Comment> getAll(Integer page, Integer itemsPerPage) {
        List<Comment> list = commentRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
        if (list.isEmpty())
            throw new NotFoundException("Comments not found");
        return list;
    }

    public Comment getById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new NotFoundException("Comment not found with id " + commentId));
    }

    @Transactional
    public Comment create(Long postId, Long authorId, Long parentCommentId, Integer level,
                          String text, String currentEmail) {
        User user = userService.getById(authorId);

        if(!(user.getEmail().equals(currentEmail)))
            throw new AuthorizationException("Forbidden action with current credentials");

        Comment newComment = new Comment();
        newComment.setPost(postService.getById(postId));
        newComment.setAuthor(user);
        newComment.setLevel(level);
        newComment.setText(text);
        if (parentCommentId != null)
            newComment.setParentComment(getById(parentCommentId));
        Comment commentForReturn = save(newComment);
        postService.increaseCountOfComments(postId);
        return commentForReturn;
    }

    @Transactional
    public Comment save(Comment newComment) {
        return commentRepository.save(newComment);
    }

    @Transactional
    public Comment update(Long commentId, String text, String currentEmail) {
        Comment commentToBeUpdated = getById(commentId);

        if(!(userService.getByEmail(currentEmail).getId().equals(commentToBeUpdated.getAuthor().getId())))
            throw new AuthorizationException("Forbidden action with current credentials");

        commentToBeUpdated.setText(text);
        commentToBeUpdated.setTimestampOfUpdating(LocalDateTime.now());
        return save(commentToBeUpdated);
    }

    @Transactional
    public void delete(Long commentId, String currentEmail) {
        Comment comment = getById(commentId);
        User user = userService.getByEmail(currentEmail);

        if(!(user.getId().equals(comment.getAuthor().getId()))
        || !(user.getRole().name().equals("ROLE_ADMIN")))
            throw new AuthorizationException("Forbidden action with current credentials");

        commentRepository.delete(comment);
        postService.decreaseCountOfComments(comment.getPost().getId()); //prop
    }
}
