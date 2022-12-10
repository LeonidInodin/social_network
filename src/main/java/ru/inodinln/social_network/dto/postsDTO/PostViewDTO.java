package ru.inodinln.social_network.dto.postsDTO;

import lombok.Data;
import ru.inodinln.social_network.dto.mediaDTO.MediaViewDTO;
import ru.inodinln.social_network.dto.usersDTO.UserReducedViewDTO;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostViewDTO {

    private Long id;

    private UserReducedViewDTO author;

    private LocalDateTime timestamp;

    private LocalDateTime timestampOfUpdating;

    private  String text;

    private List<MediaViewDTO> mediaList;

    private Long likesCount;

    private Long commentsCount;

}
