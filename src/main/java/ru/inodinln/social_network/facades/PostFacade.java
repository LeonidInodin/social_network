package ru.inodinln.social_network.facades;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ru.inodinln.social_network.dto.postsDTO.PostCreatingDTO;
import ru.inodinln.social_network.dto.postsDTO.PostUpdatingDTO;
import ru.inodinln.social_network.dto.postsDTO.PostViewDTO;
import ru.inodinln.social_network.dto.statisticsDTO.StatisticsRequestDTO;
import ru.inodinln.social_network.entities.Post;
import ru.inodinln.social_network.exceptions.ValidationService;
import ru.inodinln.social_network.services.PostService;
import ru.inodinln.social_network.utils.MapperService;

import java.time.LocalDate;
import java.util.List;

@Service
public class PostFacade {

    private final PostService postService;

    public PostFacade(PostService postService) {
        this.postService = postService;
    }

    ////////////////////////////Business methods section///////////////////////////////////////

    public List<PostViewDTO> getNewsFeedByUserId(Long userId, Integer page, Integer itemsPerPage) {
        return MapperService.mapperForCollectionOfPostViewDTO(postService.getNewsFeedByUserId(userId, page, itemsPerPage));
    }

    public List<PostViewDTO> getPostsByUserId(Long userId, Integer page, Integer itemsPerPage) {
        return MapperService.mapperForCollectionOfPostViewDTO(postService.getPostsByUserId(userId, page, itemsPerPage));
    }

    ////////////////////////////Statistics methods section///////////////////////////////////////

    public Long quantityForThePeriod(StatisticsRequestDTO dto) {
        if (dto.getEndOfPeriod() == null)
            dto.setEndOfPeriod(LocalDate.now());
        ValidationService.statisticsRequestDtoValidation(dto);
        return postService.quantityForThePeriod(dto.getStartOfPeriod(), dto.getEndOfPeriod());
    }

    public Double averageQuantityPerDayForThePeriod(StatisticsRequestDTO dto) {
        if (dto.getEndOfPeriod() == null)
            dto.setEndOfPeriod(LocalDate.now());
        ValidationService.statisticsRequestDtoValidation(dto);
        return postService.averageQuantityPerDayForThePeriod(dto.getStartOfPeriod(), dto.getEndOfPeriod());
    }

    public List<PostViewDTO> get10mostPopularByLikes(StatisticsRequestDTO dto) {
        if (dto.getEndOfPeriod() == null)
            dto.setEndOfPeriod(LocalDate.now());
        ValidationService.statisticsRequestDtoValidation(dto);
        return MapperService.mapperForCollectionOfPostViewDTO
                (postService.get10mostPopularByLikes(dto.getStartOfPeriod(), dto.getEndOfPeriod()));
    }

    public List<PostViewDTO> get10mostPopularByComments(StatisticsRequestDTO dto) {
        if (dto.getEndOfPeriod() == null)
            dto.setEndOfPeriod(LocalDate.now());
        ValidationService.statisticsRequestDtoValidation(dto);
        return MapperService.mapperForCollectionOfPostViewDTO
                (postService.get10mostPopularByComments(dto.getStartOfPeriod(), dto.getEndOfPeriod()));
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////

    public List<PostViewDTO> getAll(Integer page, Integer itemsPerPage) {
        return MapperService.mapperForCollectionOfPostViewDTO(postService.getAll(page, itemsPerPage));
    }

    public PostViewDTO getById(Long postId) {
        return MapperService.mapperForSinglePostViewDTO(postService.getById(postId));
    }

    public PostViewDTO create(PostCreatingDTO dto) {

        ValidationService.postCreatingDtoValidation(dto);
        return MapperService.mapperForSinglePostViewDTO(postService.create(dto.getAuthorId(), dto.getText()));
    }

    public PostViewDTO update(PostUpdatingDTO updatingDto) {

        ValidationService.postUpdatingDtoValidation(updatingDto);
        Post postToBeUpdated = new Post();
        BeanUtils.copyProperties(updatingDto, postToBeUpdated);
        return MapperService.mapperForSinglePostViewDTO(postService.update(postToBeUpdated));
    }

    public void delete(Long postId) {
        postService.delete(postId);
    }

}
