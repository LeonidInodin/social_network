package ru.inodinln.social_network.dto.postsDTO;

import lombok.Data;
import ru.inodinln.social_network.entities.Post;
import ru.inodinln.social_network.utils.interfaces.Convertable;

import java.time.LocalDateTime;

@Data
public class PostViewDTO implements Convertable<Post> {

    private Long id;

    private LocalDateTime dateTime;

    private  String text;

    private Long author;

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

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }
}
