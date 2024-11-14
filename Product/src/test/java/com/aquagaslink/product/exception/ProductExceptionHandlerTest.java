package com.aquagaslink.product.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ProductExceptionHandlerTest {

    @InjectMocks
    private ProductExceptionHandler handler;

    @Mock
    private HttpServletRequest request;

    @Test
    public void testHandleEntityNotFound() {
        // Mock exception
        EntityNotFoundException ex = new EntityNotFoundException("Entity not found");

        // Mock request URI
        when(request.getRequestURI()).thenReturn("/api/v1/products/123");

        // Expected response
        StandardError expectedError = new StandardError(Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                "Entity not found",
                ex.getMessage(),
                "/api/v1/products/123");

        // Call the method and assert response
        ResponseEntity<StandardError> response = handler.handleEntityNotFound(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualToComparingFieldByField(expectedError);
    }
}
