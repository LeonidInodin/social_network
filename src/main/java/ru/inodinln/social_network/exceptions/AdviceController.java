package ru.inodinln.social_network.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import ru.inodinln.social_network.exceptions.businessException.BusinessException;
import ru.inodinln.social_network.exceptions.businessException.NotFoundException;
import ru.inodinln.social_network.exceptions.securityException.AuthorizationException;
import ru.inodinln.social_network.exceptions.validationException.ValidationException;

import java.sql.SQLException;
import java.time.LocalDateTime;

@RestControllerAdvice
//@Slf4j
public class AdviceController {

    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage NotFoundException(NotFoundException ex, WebRequest request) {
       // log.error("An invalid request was rejected for reason: {}, {}", ex.getMessage(), ex.getClass());
        return new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(value = {ValidationException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorMessage ValidationException(ValidationException ex, WebRequest request) {
       // log.error("An invalid request was rejected for reason: {}, {}", ex.getMessage(), ex.getClass());
        return new ErrorMessage(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(value = {BusinessException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorMessage BusinessException(BusinessException ex, WebRequest request) {
        //log.error("An invalid request was rejected for reason: {}, {}", ex.getMessage(), ex.getClass());
        return new ErrorMessage(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(value = {DataAccessException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage DataException(DataAccessException ex, WebRequest request) {
        //log.error("An invalid request was rejected for reason: {}, {}", ex.getMessage(), ex.getClass());
        return new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(value = {InvalidFormatException.class})
    public ResponseEntity<ErrorMessage> processConversionException(InvalidFormatException ex, WebRequest request) {
        //log.error("An invalid request was rejected for reason: {}, {}", ex.getMessage(), ex.getClass());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)));
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public  ResponseEntity<ErrorMessage> getMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
       // log.error("An invalid request was rejected for reason: {}, {}", ex.getMessage(), ex.getClass());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(
                        HttpStatus.BAD_REQUEST.value(),
                        LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false)));
    }

    @ExceptionHandler(value = { SQLException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public  ResponseEntity<ErrorMessage> getSQLException(SQLException ex, WebRequest request) {
        //log.error("An invalid request was rejected for reason: {}, {}", ex.getMessage(), ex.getClass());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessage(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false)));
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public  ResponseEntity<ErrorMessage> getIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
       // log.error("An invalid request was rejected for reason: {}, {}", ex.getMessage(), ex.getClass());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessage(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false)));
    }

    @ExceptionHandler(value = {AuthorizationException.class })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public  ResponseEntity<ErrorMessage> getAuthorizationException(AuthorizationException ex, WebRequest request) {
        // log.error("An invalid request was rejected for reason: {}, {}", ex.getMessage(), ex.getClass());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorMessage(
                        HttpStatus.FORBIDDEN.value(),
                        LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false)));
    }

}
