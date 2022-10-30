package ru.inodinln.social_network.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inodinln.social_network.entities.Media;
import ru.inodinln.social_network.entities.Message;
import ru.inodinln.social_network.exceptions.NotFoundException;
import ru.inodinln.social_network.repositories.MediaRepository;
import ru.inodinln.social_network.repositories.MessageRepository;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MediaService {

    private final MediaRepository mediaRepository;

    private final PostService postService;

    private final MessageService messageService;

    String[] fileTypes = new String[] {"jpg", "png", "jpeg", "mp4", "mpg", "avi"};

    public MediaService(MediaRepository mediaRepository, PostService postService, MessageService messageService) {
        this.mediaRepository = mediaRepository;
        this.postService = postService;
        this.messageService = messageService;
    }



    ////////////////////////////Basic CRUD methods section///////////////////////////////////////
    public List<Media> findAll() {
        return mediaRepository.findAll();
    }

    public Media findById(Long mediaId) {
        Media media = mediaRepository.findById(mediaId).orElseThrow(() ->
                new NotFoundException("Media not found with id " + mediaId));
        File file = new File(media.getPath());
        media.setBase64(encodeFileToBase64Binary(file));
        return media;
    }

    @Transactional
    public void save(Long messageId, Long postId, String base64, String extension) {
        //String type = "Images";

           // type = "Images";
       // if ((post != null)&(message == null))

          //  type = "Images";
        Media media = new Media();
        media.setDateTime(LocalDateTime.now()); //WTF?
        media.setExtension(extension);
        if ((postId == null)&(messageId != null))
            media.setMessage(messageService.findById(messageId));
        else media.setPost(postService.findById(postId));
            media.setBase64(base64);

        media.setName(media.getDateTime().toString().replace(':', '_'));
        media.setPath("F:/Prog/FileStorage/" + media.getName() + "." + media.getExtension());
        //if (extension.equals("png") || extension.equals("jpg")){
          //  type = "Images";
       // }
        byte[] img = Base64.getDecoder().decode(media.getBase64());
        try (OutputStream stream = new FileOutputStream(media.getPath())) {
            stream.write(img);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        mediaRepository.save(media);
    }

    @Transactional
    public void delete(Long mediaId) {
        Media media = mediaRepository.findById(mediaId).orElseThrow(() ->
                new NotFoundException("Media not found with id " + mediaId));
        mediaRepository.delete(media);}


    ////////////////////////////Service methods section///////////////////////////////////////
   // public boolean fileTypeValidation(String extension) {

        //Arrays.stream(fileTypes).findFirst(() --> equals(extension)).orElseThrow()
   // }

    private String encodeFileToBase64Binary(File file){
        String encodedfile = null;
        try (FileInputStream fileInputStreamReader = new FileInputStream(file)){

            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = Base64.getEncoder().encodeToString(bytes);
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        return encodedfile;
    }

}
