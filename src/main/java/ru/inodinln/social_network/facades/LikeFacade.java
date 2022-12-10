package ru.inodinln.social_network.facades;

import org.springframework.stereotype.Service;
import ru.inodinln.social_network.dto.likesDTO.LikeCreatingDTO;
import ru.inodinln.social_network.dto.likesDTO.LikeViewDTO;
import ru.inodinln.social_network.exceptions.ValidationService;
import ru.inodinln.social_network.services.LikeService;
import ru.inodinln.social_network.utils.MapperService;

import java.util.List;

@Service
public class LikeFacade {

    private final LikeService likeService;

    public LikeFacade(LikeService likeService) {
        this.likeService = likeService;
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////

    public List<LikeViewDTO> getAll(Integer page, Integer itemsPerPage) {
        return MapperService.mapperForCollectionOfLikeViewDTO(likeService.getAll(page, itemsPerPage));
    }

    public LikeViewDTO getById(Long likeId) {
        return MapperService.mapperForSingleLikeViewDTO(likeService.getById(likeId));
    }

    public LikeViewDTO create(LikeCreatingDTO likeDTO) {
        ValidationService.likeCreatingDtoValidation(likeDTO);
        return MapperService.mapperForSingleLikeViewDTO(likeService.create(likeDTO.getAuthorId(), likeDTO.getPostId()));
    }

    public void delete(Long likeId) {
        likeService.delete(likeId);
    }

}
