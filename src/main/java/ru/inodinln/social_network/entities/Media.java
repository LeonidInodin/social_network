package ru.inodinln.social_network.entities;



import lombok.Data;
import ru.inodinln.social_network.utils.interfaces.Convertable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "media")
public class Media implements Convertable<Media> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;

    private  String path;

    private String name;

    private String extension;

    @Transient
    private String base64;

    @ManyToOne
    @JoinColumn(name = "post", referencedColumnName = "id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "message", referencedColumnName = "id")
    private Message message;

   @PrePersist
    private void prePersist() {
        setDateTime(LocalDateTime.now());
    }

}

