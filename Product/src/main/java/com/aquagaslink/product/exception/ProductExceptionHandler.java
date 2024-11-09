package com.aquagaslink.product.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ProductExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        StandardError error = new StandardError(Instant.now(),
                NOT_FOUND.value(),
                "Entity not found",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(NOT_FOUND).body(error);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<StandardError> handleIllegalState(IllegalStateException ex, HttpServletRequest request) {
        StandardError error = new StandardError(Instant.now(),
                BAD_REQUEST.value(),
                "Illegal state",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(BAD_REQUEST).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardError> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        StandardError error = new StandardError(Instant.now(),
                BAD_REQUEST.value(),
                "Illegal Argument",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(BAD_REQUEST).body(error);
    }

}
