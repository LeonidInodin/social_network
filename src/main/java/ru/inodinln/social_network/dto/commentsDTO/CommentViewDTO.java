package ru.inodinln.social_network.dto.commentsDTO;

import lombok.Data;
import ru.inodinln.social_network.entities.Comment;
import ru.inodinln.social_network.utils.interfaces.Convertable;

import java.time.LocalDateTime;

@Data
public class CommentViewDTO implements Convertable<Comment> {

    private Long id;

    private Long post;

    private Long author;

    private LocalDateTime dateTime;

    private  String text;

}
