package ru.inodinln.social_network.dto.likesDTO;

import lombok.Data;
import ru.inodinln.social_network.entities.Like;
import ru.inodinln.social_network.utils.interfaces.Convertable;

import java.time.LocalDateTime;

@Data
public class LikeViewDTO implements Convertable<Like> {

    private Long id;

    private LocalDateTime dateTime;

    private  Long post;

    private Long author;

}
