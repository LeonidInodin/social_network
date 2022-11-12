package ru.inodinln.social_network.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inodinln.social_network.entities.Post;
import ru.inodinln.social_network.exceptions.businessException.NotFoundException;
import ru.inodinln.social_network.repositories.PostRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() ->
                new NotFoundException("Post not found with id " + postId));
    }

    @Transactional
    public void save(Long author, String text) {
        Post post = new Post();
        post.setText(text);
        post.setAuthor(userService.findById(author));
        postRepository.save(post);
    }

    @Transactional
    public void delete(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new NotFoundException("Post not found with id " + postId));
        postRepository.delete(post);
    }
}
