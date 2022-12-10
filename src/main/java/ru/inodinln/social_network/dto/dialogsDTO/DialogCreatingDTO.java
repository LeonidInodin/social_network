package ru.inodinln.social_network.dto.dialogsDTO;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DialogCreatingDTO {

    @NotNull(message = "Field \"authorId\" is required")
    private Long authorId;

    @NotNull(message = "Field \"companionId\" is required")
    private Long companionId;

}
