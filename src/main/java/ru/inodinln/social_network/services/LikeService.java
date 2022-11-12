package ru.inodinln.social_network.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inodinln.social_network.entities.Like;
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
    public List<Like> findAll() {
        return likeRepository.findAll();
    }

    public Like findById(Long likeId) {
        return likeRepository.findById(likeId).orElseThrow(() ->
                new NotFoundException("Like not found with id " + likeId));
    }

    @Transactional
    public void save(Long authorId, Long postId) {
        Like like = new Like();
        like.setAuthor(userService.findById(authorId));
        like.setPost(postService.findById(postId));
        likeRepository.save(like);
    }

    @Transactional
    public void delete(Long likeId) {
        Like like = likeRepository.findById(likeId).orElseThrow(() ->
                new NotFoundException("Like not found with id " + likeId));
        likeRepository.delete(like);
    }
}
