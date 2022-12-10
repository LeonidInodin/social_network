package ru.inodinln.social_network.dto.subscriptionsDTO;

import lombok.Data;
import ru.inodinln.social_network.dto.usersDTO.UserReducedViewDTO;

import java.time.LocalDateTime;

@Data
public class SubscriptionViewDTO {

    private Long id;

    private LocalDateTime dateTime;

    private UserReducedViewDTO subscriber;

    private UserReducedViewDTO target;

}
