package ru.inodinln.social_network.dto.subscriptionsDTO;

import lombok.Data;

@Data
public class SubscriptionCreatingDTO {

    private Long subscriberId;

    private Long targetId;

}
