package ru.inodinln.social_network.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "conversation")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;

    private LocalDateTime actualTimestamp;

    private String name;

    private Long authorId;

    @OneToMany(mappedBy = "conversation")
    private List<Message> messages;

    @ManyToMany
    @JoinTable(name = "users_conversation",
    joinColumns = @JoinColumn(name = "conversation_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> members;

    @PrePersist
    private void prePersist() {
        timestamp = LocalDateTime.now();
        actualTimestamp = LocalDateTime.now();
    }

}
