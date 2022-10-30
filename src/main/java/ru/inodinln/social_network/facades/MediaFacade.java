package ru.inodinln.social_network.facades;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ru.inodinln.social_network.dto.MediaCreationDTO;
import ru.inodinln.social_network.dto.MediaViewDTO;
import ru.inodinln.social_network.entities.Media;
import ru.inodinln.social_network.services.MediaService;

import java.util.ArrayList;
import java.util.List;

@Service
public class MediaFacade {

    private final MediaService mediaService;

    public MediaFacade(MediaService mediaService){
        this.mediaService = mediaService;
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////

    public List<MediaViewDTO> findAll(){
       return packListDTO(mediaService.findAll());
    }

   public MediaViewDTO findById(Long mediaId){
        return packDTO(mediaService.findById(mediaId));
    }

    public void save(MediaCreationDTO mediaDTO) {
        mediaService.save(mediaDTO.getMessage(), mediaDTO.getPost(), mediaDTO.getBase64(), mediaDTO.getExtension());
    }

    ////////////////////////////Service methods section///////////////////////////////////////

    public List<MediaViewDTO> packListDTO(List<Media> listOfMedia){
        List<MediaViewDTO> listOfDTO = new ArrayList<>(listOfMedia.size());
        for (Media media : listOfMedia) {

            MediaViewDTO dto = new MediaViewDTO();
            BeanUtils.copyProperties(media, dto);
            if ((media.getPost() == null)&(media.getMessage() != null)) {
                dto.setMessage(media.getMessage().getId());
                dto.setPost(0L);
            }
            else {dto.setPost(media.getPost().getId());
                dto.setMessage(0L);
            }

            listOfDTO.add(dto);
        }
        return listOfDTO;
    }

    public MediaViewDTO packDTO(Media media){
        MediaViewDTO dto = new MediaViewDTO();
        BeanUtils.copyProperties(media, dto);
        if ((media.getPost() == null)&(media.getMessage() != null)) {
            dto.setMessage(media.getMessage().getId());
            dto.setPost(0L);
        }
        else {dto.setPost(media.getPost().getId());
            dto.setMessage(0L);
        }
        return dto;
    }
}
