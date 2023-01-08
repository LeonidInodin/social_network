package ru.inodinln.social_network.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;

    private LocalDateTime timestampOfUpdating;

    private String text;

    private Long commentsCount;

    private Long likesCount;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @OneToMany(mappedBy = "post")
    private List<Like> likes;

    @OneToMany(mappedBy = "post")
    private List<Media> media;

    @PrePersist
    private void prePersist() {
        timestamp = LocalDateTime.now();
        commentsCount = 0L;
        likesCount = 0L;
    }

}
