package ru.inodinln.social_network.dto.mediaDTO;

import lombok.Data;
import ru.inodinln.social_network.entities.Media;
import ru.inodinln.social_network.utils.interfaces.Convertable;

@Data
public class MediaCreationDTO implements Convertable<Media> {

    private Long post;

    private Long message;

    private String base64;

    private  String extension;

}
