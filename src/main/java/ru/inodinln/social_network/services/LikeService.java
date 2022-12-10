package ru.inodinln.social_network.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inodinln.social_network.entities.Like;
import ru.inodinln.social_network.entities.Post;
import ru.inodinln.social_network.exceptions.businessException.NotFoundException;
import ru.inodinln.social_network.repositories.LikeRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LikeService {

    private final LikeRepository likeRepository;

    private final UserService userService;
    private final PostService postService;

    public LikeService(LikeRepository likeRepository, UserService userService, PostService postService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////
    public List<Like> getAll(Integer page, Integer itemsPerPage) {
        return likeRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
    }

    public Like getById(Long likeId) {
        return likeRepository.findById(likeId).orElseThrow(() ->
                new NotFoundException("Like not found with id " + likeId));
    }

    @Transactional
    public Like create(Long authorId, Long postId) {
        Like newLike = new Like();
        Post post = postService.getById(postId);
        newLike.setAuthor(userService.getById(authorId));
        newLike.setPost(postService.getById(postId));
        Like likeForReturn = save(newLike);
        post.setLikesCount(post.getLikesCount()+1L);
        postService.save(post);
        return likeForReturn;
    }

    @Transactional
    public Like save(Like newLike) {
        return likeRepository.save(newLike);
    }

    @Transactional
    public void delete(Long likeId) {
        likeRepository.delete(getById(likeId));
    }
}
