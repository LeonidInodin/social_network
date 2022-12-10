package ru.inodinln.social_network.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inodinln.social_network.entities.Post;
import ru.inodinln.social_network.exceptions.businessException.NotFoundException;
import ru.inodinln.social_network.repositories.PostRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }


    ////////////////////////////Business methods section///////////////////////////////////////

    public List<Post> getNewsFeedByUserId(Long userId, Integer page, Integer itemsPerPage) {
       return postRepository.findPostsByAuthorIn(userService.getTargetsOfUserByUserId(userId),
               PageRequest.of(page, itemsPerPage, Sort.by("timestamp")))
               .orElseThrow(() -> new NotFoundException("Not found posts for newsfeed by user with id " + userId));
    }

    public List<Post> getPostsByUserId(Long userId, Integer page, Integer itemsPerPage) {
        return postRepository.findPostsByAuthor(userService.getById(userId), PageRequest.of(page, itemsPerPage))
                .orElseThrow(() -> new NotFoundException("Not found posts by user with id " + userId));
    }

    ////////////////////////////Statistics methods section///////////////////////////////////////

    public Double averageQuantityPerDayForThePeriod(LocalDate startOfPeriod, LocalDate endOfPeriod){
        return (double) quantityForThePeriod(startOfPeriod, endOfPeriod)
                / ChronoUnit.DAYS.between(endOfPeriod, startOfPeriod);

    }

    public Long quantityForThePeriod(LocalDate startOfPeriod, LocalDate endOfPeriod){
        return postRepository.findCountPostsByTimestampBetween(startOfPeriod.atStartOfDay(), endOfPeriod.atStartOfDay());

    }

    public List<Post> get10mostPopularByLikes(LocalDate startOfPeriod, LocalDate endOfPeriod) {
        return postRepository.findPostsByTimestampBetween
                (startOfPeriod.atStartOfDay(), endOfPeriod.atStartOfDay(), Sort.by("likes_count"))
                .orElseThrow(() -> new NotFoundException("Not found any posts"))
                .stream().limit(10).collect(Collectors.toList());
    }

    public List<Post> get10mostPopularByComments(LocalDate startOfPeriod, LocalDate endOfPeriod) {
        return postRepository.findPostsByTimestampBetween
                        (startOfPeriod.atStartOfDay(), endOfPeriod.atStartOfDay(), Sort.by("comments_count"))
                .orElseThrow(() -> new NotFoundException("Not found any posts"))
                .stream().limit(10).collect(Collectors.toList());

    }



    ////////////////////////////Service methods section///////////////////////////////////////

    @Transactional
    public void increaseCountOfComments(Long postId) {
        Post post = getById(postId);
        post.setCommentsCount(post.getCommentsCount() + 1L);
        save(post);
    }

    @Transactional
    public void decreaseCountOfComments(Long postId) {
        Post post = getById(postId);
        post.setCommentsCount(post.getCommentsCount() - 1L);
        if (post.getCommentsCount() < 0)
            post.setCommentsCount(0L);
        save(post);
    }

    public List<Post> getPostsByTimestampBetween(LocalDate startOfPeriod, LocalDate endOfPeriod){
        return postRepository.findPostsByTimestampBetween
                (startOfPeriod.atStartOfDay(), endOfPeriod.atStartOfDay())
                .orElseThrow(() -> new NotFoundException("Not found any posts"));
    }

    public List<Post> getPostsByUserId(Long userId) {
        return postRepository.findPostsByAuthor(userService.getById(userId))
                .orElseThrow(() -> new NotFoundException("Not found posts by user with id " + userId));
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////
    public List<Post> getAll(Integer page, Integer itemsPerPage) {
        return postRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
    }

    public Post getById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() ->
                new NotFoundException("Post not found with id " + postId));
    }

    @Transactional
    public Post create(Long authorId, String text) {
        Post newPost = new Post();
        newPost.setAuthor(userService.getById(authorId));
        newPost.setText(text);
        return save(newPost);
    }

    @Transactional
    public Post save(Post newPost) {
        return postRepository.save(newPost);
    }

    @Transactional
    public Post update(Post post) {
        Post postToBeUpdated = getById(post.getId());
        postToBeUpdated.setText(post.getText());
        postToBeUpdated.setTimestampOfUpdating(LocalDateTime.now());
        return save(postToBeUpdated);
    }

    @Transactional
    public void delete(Long postId) {
        postRepository.delete(getById(postId));
    }
}
