package ru.inodinln.social_network.entities;

import lombok.Data;
import ru.inodinln.social_network.utils.interfaces.Convertable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "messages")
public class Message implements Convertable<Message> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;

    private  String text;

    @ManyToOne
    @JoinColumn(name = "from_id", referencedColumnName = "id")
    private User from;

    @ManyToOne
    @JoinColumn(name = "to_id", referencedColumnName = "id")
    private User to;

    @OneToMany(mappedBy = "message")
    private List<Media> media;

    @PrePersist
    private void prePersist() {
        setDateTime(LocalDateTime.now());
    }

}

