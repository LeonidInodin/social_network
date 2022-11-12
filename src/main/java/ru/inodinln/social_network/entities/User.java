package ru.inodinln.social_network.entities;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.inodinln.social_network.utils.interfaces.Convertable;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User implements Convertable<User> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private LocalDate regDate;

    private LocalDate birthDate;

    private int roleId;

    private long countOfSubscribers;

    //@JsonIgnore
    @OneToMany(mappedBy = "from")
    private List<Subscription> subscriptions;

    @OneToMany(mappedBy = "from")
    private List<Message> sendedMessages;

    @OneToMany(mappedBy = "to")
    private List<Message> receivedMessages;

    @OneToMany(mappedBy = "author")
    private List<Comment> comments;

    @PrePersist
    private void prePersist() {
        setRegDate(LocalDate.now());
        setCountOfSubscribers(0);
        setRoleId(1);
    }
}
