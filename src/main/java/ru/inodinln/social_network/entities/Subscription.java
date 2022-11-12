package ru.inodinln.social_network.entities;

import lombok.Data;
import ru.inodinln.social_network.utils.interfaces.Convertable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "subscriptions")
public class Subscription implements Convertable<Subscription> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;

    private Long toId;

    @ManyToOne
    @JoinColumn(name = "from_id", referencedColumnName = "id")
    private User from;

    @PrePersist
    private void prePersist() {
        setDateTime(LocalDateTime.now());
    }

}
