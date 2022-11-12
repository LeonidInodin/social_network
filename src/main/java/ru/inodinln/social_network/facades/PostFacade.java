package ru.inodinln.social_network.facades;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ru.inodinln.social_network.dto.postsDTO.PostCreationDTO;
import ru.inodinln.social_network.dto.postsDTO.PostViewDTO;
import ru.inodinln.social_network.entities.Post;
import ru.inodinln.social_network.services.PostService;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostFacade {

    private final PostService postService;

    public PostFacade(PostService postService){
        this.postService = postService;
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////

    public List<PostViewDTO> findAll(){
        return packingListDTO(postService.findAll());
    }

    public PostViewDTO findById(Long postId){
        return packingDTO(postService.findById(postId));
    }

    public void save(PostCreationDTO postDTO){
        postService.save(postDTO.getAuthor(), postDTO.getText());
    }

    ////////////////////////////Service methods section///////////////////////////////////////

    public List<PostViewDTO> packingListDTO(List<Post> listOfPost){
        List<PostViewDTO> listOfDTO = new ArrayList<>(listOfPost.size());
        for (Post post : listOfPost) {

            PostViewDTO DTO = new PostViewDTO();
            BeanUtils.copyProperties(post, DTO);
            DTO.setAuthor(post.getAuthor().getId());
            listOfDTO.add(DTO);
        }
        return listOfDTO;
    }

    public PostViewDTO packingDTO(Post post){
        PostViewDTO DTO = new PostViewDTO();
        BeanUtils.copyProperties(post, DTO);
        DTO.setAuthor(post.getAuthor().getId());
        return DTO;
    }
}
