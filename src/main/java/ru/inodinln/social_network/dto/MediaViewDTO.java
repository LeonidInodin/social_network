package ru.inodinln.social_network.dto;

import java.time.LocalDateTime;

public class MediaViewDTO {

    private Long id;

    private Long post;

    private Long message;

    private String base64;

    private  String extension;

    private LocalDateTime dateTime;

    public Long getPost() {
        return post;
    }

    public void setPost(Long post) {
        this.post = post;
    }

    public Long getMessage() {
        return message;
    }

    public void setMessage(Long message) {
        this.message = message;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
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
}
