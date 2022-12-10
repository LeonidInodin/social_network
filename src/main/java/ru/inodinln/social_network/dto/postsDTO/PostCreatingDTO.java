package ru.inodinln.social_network.dto.postsDTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PostCreatingDTO {

    @NotNull
    private Long authorId;

    @NotBlank
    @Size(max = 1000, message = "Field \"text\" must contains maximum 1000 symbols")
    private String text;

    //media

}
