package ru.inodinln.social_network.dto.dialogsDTO;

import lombok.Data;
import ru.inodinln.social_network.dto.messagesDTO.MessageViewDTO;

import java.time.LocalDateTime;

@Data
public class DialogViewDTO {

    private Long id;

    private Long authorId;

    private Long companionId;

    private LocalDateTime actualTimestamp;

    private LocalDateTime timestamp;

    private MessageViewDTO lastMessage;

}
