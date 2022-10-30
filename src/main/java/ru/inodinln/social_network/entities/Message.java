package ru.inodinln.social_network.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "messages")
public class Message {

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }
}

