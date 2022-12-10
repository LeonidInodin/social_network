package ru.inodinln.social_network.facades;

import org.springframework.stereotype.Service;
import ru.inodinln.social_network.dto.commentsDTO.CommentCreatingDTO;
import ru.inodinln.social_network.dto.commentsDTO.CommentUpdatingDTO;
import ru.inodinln.social_network.dto.commentsDTO.CommentViewDTO;
import ru.inodinln.social_network.dto.commentsDTO.CommentsTreeViewDTO;
import ru.inodinln.social_network.entities.Comment;
import ru.inodinln.social_network.exceptions.ValidationService;
import ru.inodinln.social_network.services.CommentService;
import ru.inodinln.social_network.utils.MapperService;

import java.util.List;

@Service
public class CommentFacade {

    private final CommentService commentService;

    public CommentFacade(CommentService commentService) {
        this.commentService = commentService;
    }


    ////////////////////////////Business methods section///////////////////////////////////////
    public List<CommentsTreeViewDTO> getCommentsTreeByPostId(Long postId, Integer page, Integer itemsPerPage) {
        List<Comment> list = commentService.getCommentsTreeByPostId(postId, page, itemsPerPage);
        return MapperService.mapperForCommentsTreeDTO(list);
    }

    public List<CommentViewDTO> getCommentsByPostId(Long postId, Integer page, Integer itemsPerPage) {
        return MapperService.mapperForCollectionOfCommentViewDTO(commentService.getCommentsByPostId(postId, page, itemsPerPage));
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////

    public List<CommentViewDTO> getAll(Integer page, Integer itemsPerPage) {
        return MapperService.mapperForCollectionOfCommentViewDTO(commentService.getAll(page, itemsPerPage));
    }

    public CommentViewDTO getById(Long commentId) {
        return MapperService.mapperForSingleCommentViewDTO(commentService.getById(commentId));
    }

    public CommentViewDTO create(CommentCreatingDTO creatingDto) {

        ValidationService.commentCreatingDtoValidation(creatingDto);

        return MapperService.mapperForSingleCommentViewDTO(commentService.create(creatingDto.getPost(),
                creatingDto.getAuthor(), creatingDto.getParentComment(), creatingDto.getLevel(), creatingDto.getText()));
    }

    public CommentViewDTO update(CommentUpdatingDTO updatingDTO) {

        return MapperService.mapperForSingleCommentViewDTO(commentService.update(updatingDTO.getId(), updatingDTO.getText()));
    }

    public void delete(Long commentId) {
        commentService.delete(commentId);
    }

}
