package ru.inodinln.social_network.dto.mediaDTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MediaViewDTO {

    private Long id;

    private Long postId;

    private Long messageId;

    private String base64;

    private  String extension;

    private LocalDateTime timestamp;

}
