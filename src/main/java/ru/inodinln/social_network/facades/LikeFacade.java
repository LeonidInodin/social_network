package ru.inodinln.social_network.facades;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ru.inodinln.social_network.dto.likesDTO.LikeCreationDTO;
import ru.inodinln.social_network.dto.likesDTO.LikeViewDTO;
import ru.inodinln.social_network.entities.Like;
import ru.inodinln.social_network.services.LikeService;

import java.util.ArrayList;
import java.util.List;

@Service
public class LikeFacade {

    private final LikeService likeService;

    public LikeFacade(LikeService likeService){
        this.likeService = likeService;
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////

    public List<LikeViewDTO> findAll(){
        return packListDTO(likeService.findAll());
    }

    public LikeViewDTO findById(Long likeId){
        return packingDTO(likeService.findById(likeId));
    }

    public void save(LikeCreationDTO likeDTO){
        likeService.save(likeDTO.getAuthor(), likeDTO.getPost());
    }

    ////////////////////////////Service methods section///////////////////////////////////////

    public List<LikeViewDTO> packListDTO(List<Like> listOfLike){
        List<LikeViewDTO> listOfDTO = new ArrayList<>(listOfLike.size());
        for (Like like : listOfLike) {

            LikeViewDTO dto = new LikeViewDTO();
            BeanUtils.copyProperties(like, dto);
            dto.setAuthor(like.getAuthor().getId());
            dto.setPost(like.getPost().getId());
            listOfDTO.add(dto);
        }
        return listOfDTO;
    }

    public LikeViewDTO packingDTO(Like like){
        LikeViewDTO dto = new LikeViewDTO();
        BeanUtils.copyProperties(like, dto);
        dto.setAuthor(like.getAuthor().getId());
        dto.setPost(like.getPost().getId());
        return dto;
    }
}
