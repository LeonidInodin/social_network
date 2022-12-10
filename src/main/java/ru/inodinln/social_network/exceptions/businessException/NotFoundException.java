package ru.inodinln.social_network.exceptions.businessException;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}
