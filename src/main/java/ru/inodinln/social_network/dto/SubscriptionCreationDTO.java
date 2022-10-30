package ru.inodinln.social_network.dto;

public class SubscriptionCreationDTO {

    private Long toId;

    private Long fromId;

    public Long getToId() {
        return toId;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }
}
