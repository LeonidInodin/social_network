package ru.inodinln.social_network.dto.messagesDTO;

import lombok.Data;
import ru.inodinln.social_network.entities.Message;
import ru.inodinln.social_network.utils.interfaces.Convertable;

@Data
public class MessageCreationDTO implements Convertable<Message> {

    private Long from;

    private Long to;

    private String text;

}
