package ru.inodinln.social_network.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DataException extends RuntimeException{
    public DataException(String message) {
        super(message);
    }
}
