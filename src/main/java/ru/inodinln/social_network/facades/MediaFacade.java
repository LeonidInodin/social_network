package ru.inodinln.social_network.facades;

import org.springframework.stereotype.Service;
import ru.inodinln.social_network.dto.mediaDTO.MediaCreatingDTO;
import ru.inodinln.social_network.dto.mediaDTO.MediaViewDTO;
import ru.inodinln.social_network.exceptions.ValidationService;
import ru.inodinln.social_network.services.MediaService;
import ru.inodinln.social_network.utils.MapperService;

import java.util.List;

@Service
public class MediaFacade {

    private final MediaService mediaService;

    public MediaFacade(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    ////////////////////////////Basic CRUD methods section///////////////////////////////////////

    public List<MediaViewDTO> getAll(Integer page, Integer itemsPerPage) {
        return MapperService.mapperForCollectionOfMediaViewDTO(mediaService.getAll(page, itemsPerPage));
    }

    public MediaViewDTO getById(Long mediaId) {
        return MapperService.mapperForSingleMediaViewDTO(mediaService.getById(mediaId));
    }

    public MediaViewDTO create(MediaCreatingDTO dto) {
        ValidationService.mediaCreatingDtoValidation(dto);
        return MapperService.mapperForSingleMediaViewDTO(mediaService.create
                (dto.getMessageId(), dto.getPostId(), dto.getBase64(), dto.getExtension()));
    }

    public void delete(Long mediaId) {
        mediaService.delete(mediaId);
    }
}
