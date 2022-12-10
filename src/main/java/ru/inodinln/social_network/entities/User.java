package ru.inodinln.social_network.entities;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private LocalDate regDate;

    private LocalDate birthDate;

    private Integer roleId;

    private Long countOfSubscribers;

    @ManyToMany(mappedBy = "members")
    private List<Conversation> conversations;

    @OneToMany(mappedBy = "author")
    private List<Dialog> authorDialogs;

    @OneToMany(mappedBy = "companion")
    private List<Dialog> companionDialogs;

    @OneToMany(mappedBy = "target")
    private List<Subscription> subscriptionsToItself;

    @OneToMany(mappedBy = "subscriber")
    private List<Subscription> subscriptionsToOther;

    @OneToMany(mappedBy = "sender")
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "recipient")
    private List<Message> receivedMessages;

    @OneToMany(mappedBy = "author")
    private List<Comment> comments;

    @OneToMany(mappedBy = "author")
    private List<Like> likes;


    @PrePersist
    private void prePersist() {
        regDate = LocalDate.now();
        countOfSubscribers = 0L;
        roleId = 1;
    }

}
