package ru.inodinln.social_network.entities;

import lombok.Data;
import ru.inodinln.social_network.utils.interfaces.Convertable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "likes")
public class Like implements Convertable<Like> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;

    private String text;

    private Long likesCount;

    @ManyToOne
    @JoinColumn(name = "author", referencedColumnName = "id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "post", referencedColumnName = "id")
    private Post post;

    @PrePersist
    private void prePersist() {
        setDateTime(LocalDateTime.now());
    }

}
