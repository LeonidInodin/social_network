package ru.inodinln.social_network.dto.mediaDTO;

import lombok.Data;
import ru.inodinln.social_network.entities.Media;
import ru.inodinln.social_network.utils.interfaces.Convertable;

import java.time.LocalDateTime;

@Data
public class MediaViewDTO implements Convertable<Media> {

    private Long id;

    private Long post;

    private Long message;

    private String base64;

    private  String extension;

    private LocalDateTime dateTime;

}
