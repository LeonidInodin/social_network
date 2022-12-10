package ru.inodinln.social_network.dto.mediaDTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MediaCreatingDTO {


    private Long postId;

    private Long messageId;

    @NotBlank(message = "Field \"base64\" is required")
    private String base64;

    @NotBlank(message = "Field \"extension\" is required")
    private  String extension;

}
