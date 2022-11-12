package ru.inodinln.social_network.dto.subscriptionsDTO;

import lombok.Data;
import ru.inodinln.social_network.entities.Subscription;
import ru.inodinln.social_network.utils.interfaces.Convertable;

@Data
public class SubscriptionCreationDTO implements Convertable<Subscription> {

    private Long toId;

    private Long fromId;

}
