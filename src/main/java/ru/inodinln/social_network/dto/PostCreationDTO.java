package ru.inodinln.social_network.dto;

public class PostCreationDTO {

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
