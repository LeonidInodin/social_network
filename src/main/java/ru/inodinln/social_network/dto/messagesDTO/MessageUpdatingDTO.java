package ru.inodinln.social_network.dto.messagesDTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class MessageUpdatingDTO {

    @NotNull
    private Long id;

    @NotBlank
    @Size(max = 1000, message = "Field \"text\" must contains maximum 1000 symbols")
    private String text;

}
