package ru.inodinln.social_network.exceptions.securityException;

public class AuthorizationException extends RuntimeException{
    public AuthorizationException(String message) {
        super(message);
    }
}
