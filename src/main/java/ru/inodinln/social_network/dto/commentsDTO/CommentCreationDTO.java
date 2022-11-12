package ru.inodinln.social_network.dto.commentsDTO;

import lombok.Data;
import ru.inodinln.social_network.entities.Comment;
import ru.inodinln.social_network.utils.interfaces.Convertable;

@Data
public class CommentCreationDTO implements Convertable<Comment> {

    private Long post;

    private Long author;

    private String text;

}
