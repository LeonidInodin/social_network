package ru.inodinln.social_network.entities;

import lombok.Data;
import ru.inodinln.social_network.utils.interfaces.Convertable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "posts")
public class Post implements Convertable<Post> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;

    private String text;

    private Long likesCount;

    @ManyToOne
    @JoinColumn(name = "author", referencedColumnName = "id")
    private User author;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @OneToMany(mappedBy = "post")
    private List<Like> likes;

    @OneToMany(mappedBy = "post")
    private List<Media> media;

    @PrePersist
    private void prePersist() {
        setDateTime(LocalDateTime.now());
        setLikesCount(0L);
    }

}
