package ru.inodinln.social_network.dto.likesDTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LikeViewDTO {

    private Long id;

    private LocalDateTime timestamp;

    private  Long postId;

    private Long authorId;

}
