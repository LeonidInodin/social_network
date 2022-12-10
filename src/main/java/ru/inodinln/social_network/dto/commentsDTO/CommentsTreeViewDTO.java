package ru.inodinln.social_network.dto.commentsDTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentsTreeViewDTO {

    private Long id;

    private Long post;

    private Long author;

    private LocalDateTime timestamp;

    private LocalDateTime timestampOfUpdating;

    private Integer level;

    private String text;

    private Long comment;

    private List<CommentsTreeViewDTO> comments;

}
