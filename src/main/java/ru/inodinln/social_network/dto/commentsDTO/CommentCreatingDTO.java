package ru.inodinln.social_network.dto.commentsDTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CommentCreatingDTO {

    @NotNull(message = "Field \"Post\" is required")
    private Long post;

    private Long parentComment;

    @NotNull(message = "Field \"Author\" is required")
    private Long author;

    private Integer level;

    @NotBlank(message = "Field \"Text\" is required")
    @Size(max = 500, message = "Field \"Text\" must contains maximum 500 letters")
    private String text;

}
