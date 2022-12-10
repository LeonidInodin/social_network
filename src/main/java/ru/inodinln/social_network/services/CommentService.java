package ru.inodinln.social_network.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inodinln.social_network.entities.Comment;
import ru.inodinln.social_network.exceptions.businessException.NotFoundException;
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
    public Comment create(Long postId, Long authorId, Long parentCommentId, Integer level, String text) {
        Comment newComment = new Comment();
        newComment.setPost(postService.getById(postId));
        newComment.setAuthor(userService.getById(authorId));
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
    public Comment update(Long commentId, String text) {
        Comment commentToBeUpdated = getById(commentId);
        commentToBeUpdated.setText(text);
        commentToBeUpdated.setTimestampOfUpdating(LocalDateTime.now());
        return save(commentToBeUpdated);
    }

    @Transactional
    public void delete(Long commentId) {
        Comment comment = getById(commentId);
        commentRepository.delete(comment);
        postService.decreaseCountOfComments(comment.getPost().getId());
    }
}
