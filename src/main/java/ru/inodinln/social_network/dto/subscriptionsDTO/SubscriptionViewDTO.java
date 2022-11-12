package ru.inodinln.social_network.dto.subscriptionsDTO;

import lombok.Data;
import ru.inodinln.social_network.entities.Subscription;
import ru.inodinln.social_network.utils.interfaces.Convertable;

import java.time.LocalDateTime;

@Data
public class SubscriptionViewDTO implements Convertable<Subscription> {

    private Long id;

    private LocalDateTime dateTime;

    private Long fromId;

    private Long toId;

}
