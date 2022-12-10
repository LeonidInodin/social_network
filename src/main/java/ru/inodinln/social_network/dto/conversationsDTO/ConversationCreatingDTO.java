package ru.inodinln.social_network.dto.conversationsDTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ConversationCreatingDTO {

    @NotNull(message = "Field \"authorId\" is required")
    private Long authorId;


    @NotBlank
    @Size(max = 20, message = "Field \"name\" must contains maximum 20 symbols")
    private String name;


}
