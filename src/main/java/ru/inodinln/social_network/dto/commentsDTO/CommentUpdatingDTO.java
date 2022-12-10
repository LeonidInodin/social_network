package ru.inodinln.social_network.dto.commentsDTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CommentUpdatingDTO {

    @NotNull(message = "Field \"id\" is required")
    private Long id;

    @NotBlank
    @Size(max = 500, message = "Field \"text\" must contains maximum 500 letters")
    private  String text;

}
