package ru.inodinln.social_network.dto.messagesDTO;

import lombok.Data;
import ru.inodinln.social_network.entities.Message;
import ru.inodinln.social_network.utils.interfaces.Convertable;
import java.time.LocalDateTime;

@Data
public class MessageViewDTO implements Convertable<Message> {

    private Long id;

    private LocalDateTime dateTime;

    private  String text;

    private Long from;

    private Long to;

}
