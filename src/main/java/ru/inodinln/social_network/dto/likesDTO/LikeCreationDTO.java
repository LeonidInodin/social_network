package ru.inodinln.social_network.dto.likesDTO;

import lombok.Data;
import ru.inodinln.social_network.entities.Like;
import ru.inodinln.social_network.utils.interfaces.Convertable;

@Data
public class LikeCreationDTO implements Convertable<Like> {

    private Long post;

    private Long author;

}
