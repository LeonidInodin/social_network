package ru.inodinln.social_network.facades;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ru.inodinln.social_network.dto.CommentCreationDTO;
import ru.inodinln.social_network.dto.CommentViewDTO;
import ru.inodinln.social_network.dto.MessageCreationDTO;
import ru.inodinln.social_network.dto.MessageViewDTO;
import ru.inodinln.social_network.entities.Comment;
import ru.inodinln.social_network.entities.Message;
import ru.inodinln.social_network.services.CommentService;
import ru.inodinln.social_network.services.MessageService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentFacade {

    private final CommentService commentService;

    public CommentFacade(CommentService commentService){
        this.commentService = commentService;
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////

    public List<CommentViewDTO> findAll(){
        return packingListDTO(commentService.findAll());
    }

    public CommentViewDTO findById(Long commentId){
        return packingDTO(commentService.findById(commentId));
    }

    public void save(CommentCreationDTO commentDTO){
        commentService.save(commentDTO.getPost(), commentDTO.getAuthor(), commentDTO.getText());
    }

    ////////////////////////////Service methods section///////////////////////////////////////

    public List<CommentViewDTO> packingListDTO(List<Comment> listOfComm){
        List<CommentViewDTO> listOfDTO = new ArrayList<>(listOfComm.size());
        for (Comment comm : listOfComm) {

            CommentViewDTO dto = new CommentViewDTO();
            BeanUtils.copyProperties(comm, dto);
            dto.setPost(comm.getPost().getId());
            dto.setAuthor(comm.getAuthor().getId());
            listOfDTO.add(dto);
        }
        return listOfDTO;
    }

    public CommentViewDTO packingDTO(Comment comm){
        CommentViewDTO dto = new CommentViewDTO();
        BeanUtils.copyProperties(comm, dto);
        dto.setPost(comm.getPost().getId());
        dto.setAuthor(comm.getAuthor().getId());
        return dto;
    }
}
