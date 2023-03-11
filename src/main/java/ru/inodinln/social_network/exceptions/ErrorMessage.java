package ru.inodinln.social_network.exceptions;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorMessage {

    private int statusCode;
    private LocalDateTime timestamp;
    private String exceptionClass;
    private String message;
    private String path;

    public ErrorMessage(int statusCode, LocalDateTime timestamp, String exceptionClass, String message, String path ) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.exceptionClass = exceptionClass;
        this.message = message;
        this.path = path;
    }
}
