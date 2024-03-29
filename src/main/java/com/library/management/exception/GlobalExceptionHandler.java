package com.library.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Error> handBookNotFoundException(ResourceNotFoundException resourceNotFoundException, WebRequest webRequest) {
        return new ResponseEntity<>(
                new Error(resourceNotFoundException.getMessage(), LocalDate.now(), webRequest.getDescription(false)),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LimitExceededException.class)
    public ResponseEntity<Error> handBookNotFoundException(LimitExceededException limitExceededException, WebRequest webRequest) {
        return new ResponseEntity<>(
                new Error(limitExceededException.getMessage(), LocalDate.now(), webRequest.getDescription(false)),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleAllException(Exception ex, WebRequest webRequest) {
        return new ResponseEntity<>(
                new Error(ex.getMessage(), LocalDate.now(), webRequest.getDescription(false)),
                HttpStatus.NOT_FOUND);
    }
}
