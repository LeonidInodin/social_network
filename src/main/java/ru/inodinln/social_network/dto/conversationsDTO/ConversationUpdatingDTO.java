package ru.inodinln.social_network.dto.conversationsDTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ConversationUpdatingDTO {

    @NotNull(message = "Field \"id\" is required")
    private Long id;


    @NotBlank
    @Size(max = 20, message = "Field \"name\" must contains maximum 20 symbols")
    private String name;

}
