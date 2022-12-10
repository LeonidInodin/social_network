package ru.inodinln.social_network.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inodinln.social_network.entities.Media;
import ru.inodinln.social_network.exceptions.businessException.NotFoundException;
import ru.inodinln.social_network.repositories.MediaRepository;

import java.io.*;
import java.util.Base64;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MediaService {

    private final MediaRepository mediaRepository;

    private final PostService postService;

    private final MessageService messageService;

    public MediaService(MediaRepository mediaRepository, PostService postService, MessageService messageService) {
        this.mediaRepository = mediaRepository;
        this.postService = postService;
        this.messageService = messageService;
    }



    ////////////////////////////Basic CRUD methods section///////////////////////////////////////
    public List<Media> getAll(Integer page, Integer itemsPerPage) {
        return mediaRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
    }

    public Media getById(Long mediaId) {
        Media media = mediaRepository.findById(mediaId).orElseThrow(() ->
                new NotFoundException("Media not found with id " + mediaId));
        File file = new File(media.getPath());
        media.setBase64(encodeFileToBase64Binary(file));
        return media;
    }

    @Transactional
    public Media create(Long messageId, Long postId, String base64, String extension) {

        Media media = new Media();
        media.setExtension(extension);
        if (postId == null) {
            media.setMessage(messageService.getById(messageId));
            media.setPost(null);
        }
        else {
            media.setPost(postService.getById(postId));
            media.setMessage(null);
        }
        media.setBase64(base64);

        media.setName(media.getTimestamp().toString().replace(':', '_'));
        media.setPath("D:/Prog/FileStorage/" + media.getName() + "." + media.getExtension());

        byte[] mediaFile = Base64.getDecoder().decode(media.getBase64());
        try (OutputStream stream = new FileOutputStream(media.getPath())) {
            stream.write(mediaFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mediaRepository.save(media);
    }

    @Transactional
    public void delete(Long mediaId) {
        mediaRepository.delete(getById(mediaId));}


    ////////////////////////////Service methods section///////////////////////////////////////

    private String encodeFileToBase64Binary(File file){
        String base64string = null;
        try (FileInputStream fileInputStreamReader = new FileInputStream(file)){

            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            base64string = Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {

            e.printStackTrace();
        }

        return base64string;
    }

}
