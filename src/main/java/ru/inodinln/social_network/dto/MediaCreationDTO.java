package ru.inodinln.social_network.dto;

public class MediaCreationDTO {

    private Long post;

    private Long message;

    private String base64;

    private  String extension;

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
}
