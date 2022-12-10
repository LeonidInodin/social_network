package ru.inodinln.social_network.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "target_id", referencedColumnName = "id")
    private User target;

    @ManyToOne
    @JoinColumn(name = "subscriber_id", referencedColumnName = "id")
    private User subscriber;

    @PrePersist
    private void prePersist() {
        timestamp = LocalDateTime.now();
    }

}
