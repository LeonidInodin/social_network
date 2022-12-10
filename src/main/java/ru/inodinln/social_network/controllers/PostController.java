package ru.inodinln.social_network.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.inodinln.social_network.dto.postsDTO.PostCreatingDTO;
import ru.inodinln.social_network.dto.postsDTO.PostUpdatingDTO;
import ru.inodinln.social_network.dto.postsDTO.PostViewDTO;
import ru.inodinln.social_network.dto.statisticsDTO.StatisticsRequestDTO;
import ru.inodinln.social_network.facades.PostFacade;

import java.util.List;

@RestController
@RequestMapping("/API/v1/posts")
public class PostController {

    private final PostFacade postFacade;

    public PostController(PostFacade postFacade) {
        this.postFacade = postFacade;
    }

    ////////////////////////////Business methods section///////////////////////////////////////

    @GetMapping("/newsFeed/{userId}")
    public ResponseEntity<List<PostViewDTO>> getNewsFeedByUserId
            (@PathVariable("userId") Long userId,
             @RequestParam(required = false, defaultValue = "0") Integer page,
             @RequestParam(required = false, defaultValue = "10") Integer itemsPerPage) {
        return new ResponseEntity<>(postFacade.getNewsFeedByUserId(userId, page, itemsPerPage), HttpStatus.OK);
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<PostViewDTO>> getPostsByUserId
            (@PathVariable("userId") Long userId,
             @RequestParam(required = false, defaultValue = "0") Integer page,
             @RequestParam(required = false, defaultValue = "10") Integer itemsPerPage) {
        return new ResponseEntity<>(postFacade.getPostsByUserId(userId, page, itemsPerPage), HttpStatus.OK);
    }

    ////////////////////////////Statistics methods section///////////////////////////////////////

    @GetMapping("/statistics/quantityForThePeriod")
    public ResponseEntity<Long> quantityForThePeriod(@RequestBody StatisticsRequestDTO requestDto){
        return new ResponseEntity<>(postFacade.quantityForThePeriod(requestDto), HttpStatus.OK);
    }

    @GetMapping("/statistics/averageQuantityPerDayForThePeriod")
    public ResponseEntity<Double> averageQuantityPerDayForThePeriod(@RequestBody StatisticsRequestDTO requestDto){
        return new ResponseEntity<>(postFacade.averageQuantityPerDayForThePeriod(requestDto), HttpStatus.OK);
    }

    @GetMapping("/statistics/mostPopularByLikes")
    public ResponseEntity<List<PostViewDTO>> get10mostPopularByLikes(@RequestBody StatisticsRequestDTO requestDto){
        return new ResponseEntity<>(postFacade.get10mostPopularByLikes(requestDto), HttpStatus.OK);
    }

    @GetMapping("/statistics/mostPopularByComments")
    public ResponseEntity<List<PostViewDTO>> get10mostPopularByComments(@RequestBody StatisticsRequestDTO requestDto){
        return new ResponseEntity<>(postFacade.get10mostPopularByComments(requestDto), HttpStatus.OK);
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////
    @GetMapping
    public ResponseEntity<List<PostViewDTO>> getAll
    (@RequestParam(required = false, defaultValue = "0") Integer page,
     @RequestParam(required = false, defaultValue = "10") Integer itemsPerPage) {
        return new ResponseEntity<>(postFacade.getAll(page, itemsPerPage), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostViewDTO> getById(@PathVariable("postId") Long postId) {
        return new ResponseEntity<>(postFacade.getById(postId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostViewDTO> create(@RequestBody PostCreatingDTO postDTO) {
        return new ResponseEntity<>(postFacade.create(postDTO), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<PostViewDTO> update(@RequestBody PostUpdatingDTO updateDto) {
        return new ResponseEntity<>(postFacade.update(updateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(@PathVariable("postId") Long postId) {
        postFacade.delete(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
