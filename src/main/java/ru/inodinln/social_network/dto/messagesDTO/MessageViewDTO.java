package ru.inodinln.social_network.dto.messagesDTO;

import lombok.Data;
import ru.inodinln.social_network.dto.mediaDTO.MediaViewDTO;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MessageViewDTO {

    private Long id;

    private LocalDateTime timestamp;

    private LocalDateTime timestampOfUpdating;

    private  String text;

    private Long conversationId;

    private Long dialogId;

    private Long senderId;

    private Long recipientId;

    private List<MediaViewDTO> media;

}
