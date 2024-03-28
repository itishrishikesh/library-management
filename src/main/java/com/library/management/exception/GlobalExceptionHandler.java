package com.library.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResourceNotFoundException> handBookNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        return new ResponseEntity<>(resourceNotFoundException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LimitExceededException.class)
    public ResponseEntity<LimitExceededException> handBookNotFoundException(LimitExceededException limitExceededException) {
        return new ResponseEntity<>(limitExceededException, HttpStatus.NOT_FOUND);
    }
}
