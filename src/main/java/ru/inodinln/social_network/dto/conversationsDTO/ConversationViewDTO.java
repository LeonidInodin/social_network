package ru.inodinln.social_network.dto.conversationsDTO;

import lombok.Data;
import ru.inodinln.social_network.dto.messagesDTO.MessageViewDTO;
import ru.inodinln.social_network.dto.usersDTO.UserReducedViewDTO;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ConversationViewDTO {

    private Long id;

    private Long authorId;

    private LocalDateTime actualTimestamp;

    private LocalDateTime timestamp;

    private String name;

    private Long membersCount;

    private List<UserReducedViewDTO> members;//first 3 members

    private MessageViewDTO lastMessage;

}
