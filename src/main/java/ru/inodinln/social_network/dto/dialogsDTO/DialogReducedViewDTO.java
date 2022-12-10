package ru.inodinln.social_network.dto.dialogsDTO;

import lombok.Data;
import ru.inodinln.social_network.dto.messagesDTO.MessageViewDTO;
import ru.inodinln.social_network.dto.usersDTO.UserReducedViewDTO;

import java.time.LocalDateTime;

@Data
public class DialogReducedViewDTO {

    private Long id;

    private UserReducedViewDTO companion;

    private LocalDateTime actualTimestamp;

    private MessageViewDTO lastMessage;

}
