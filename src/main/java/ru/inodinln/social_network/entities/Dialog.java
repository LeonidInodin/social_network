package ru.inodinln.social_network.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "dialog")
public class Dialog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;

    private LocalDateTime actualTimestamp;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "companion_id", referencedColumnName = "id")
    private User companion;

    @OneToMany(mappedBy = "dialog")
    private List<Message> messages;

    @PrePersist
    private void prePersist() {
        timestamp = LocalDateTime.now();
        actualTimestamp = LocalDateTime.now();
    }

}
