package com.aquagaslink.client.exception;

import com.aquagaslink.client.exception.ClientExceptionHandler;
import com.aquagaslink.client.exception.StandardError;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientExceptionHandlerTest {

    @InjectMocks
    private ClientExceptionHandler handler;

    @Mock
    private HttpServletRequest request;

    @Test
    public void testHandleEntityNotFound() {
        // Mock exception and request data
        EntityNotFoundException ex = new EntityNotFoundException("Entity not found");
        when(request.getRequestURI()).thenReturn("/api/v1/clients/1");

        // Call the handler method
        ResponseEntity<StandardError> response = handler.handleEntityNotFound(ex, request);

        // Verify response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        StandardError error = response.getBody();
        assertEquals(404, error.getStatus());
        assertEquals("Entity not found", error.getError());
        assertEquals("Entity not found", error.getMessage());
        assertEquals("/api/v1/clients/1", error.getPath());
    }

    @Test
    public void testHandleIllegalState() {
        // Mock exception and request data
        IllegalStateException ex = new IllegalStateException("Illegal state");
        when(request.getRequestURI()).thenReturn("/api/v1/clients");

        // Call the handler method
        ResponseEntity<StandardError> response = handler.handleIllegalState(ex, request);

        // Verify response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        StandardError error = response.getBody();
        assertEquals(400, error.getStatus());
        assertEquals("Illegal state", error.getError());
        assertEquals("Illegal state", error.getMessage());
        assertEquals("/api/v1/clients", error.getPath());
    }

    @Test
    public void testHandleIllegalArgument() {
        // Mock exception and request data
        IllegalArgumentException ex = new IllegalArgumentException("Illegal argument");
        when(request.getRequestURI()).thenReturn("/api/v1/clients/search");

        // Call the handler method
        ResponseEntity<StandardError> response = handler.handleIllegalArgument(ex, request);

        // Verify response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        StandardError error = response.getBody();
        assertEquals(400, error.getStatus());
        assertEquals("Illegal Argument", error.getError());
        assertEquals("Illegal argument", error.getMessage());
        assertEquals("/api/v1/clients/search", error.getPath());
    }
}