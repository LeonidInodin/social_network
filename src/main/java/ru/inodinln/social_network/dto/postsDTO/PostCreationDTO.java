package ru.inodinln.social_network.dto.postsDTO;

import lombok.Data;
import ru.inodinln.social_network.entities.Post;
import ru.inodinln.social_network.utils.interfaces.Convertable;

@Data
public class PostCreationDTO implements Convertable<Post> {

    private Long author;

    private String text;

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
