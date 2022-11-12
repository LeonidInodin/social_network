package ru.inodinln.social_network.exceptions;

import lombok.Data;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Data
public class ErrorMessage {

    private int statusCode;
    private LocalDateTime timestamp;
    private String message;
    private String path;

    public ErrorMessage(int statusCode, LocalDateTime timestamp, String message, String path ) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
        this.path = path;
    }
}
