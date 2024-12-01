//package com.aquagaslink.product.exception;
//
//import jakarta.persistence.EntityNotFoundException;
//import jakarta.servlet.http.HttpServletRequest;
//import org.junit.Assert;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.time.Instant;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.http.HttpStatus.BAD_REQUEST;
//import static org.springframework.http.HttpStatus.NOT_FOUND;
//
//public class ProductExceptionHandlerTest {
//
//    @InjectMocks
//    private ProductExceptionHandler handler;
//
//    @Mock
//    private HttpServletRequest request;
//
//    AutoCloseable openMocks;
//
//    @BeforeEach
//    void setup(){
//        openMocks = MockitoAnnotations.openMocks(this);
//    }
//
//    @AfterEach
//    void tearDown() throws Exception {
//        openMocks.close();
//    }
//
//    @Test
//    public void testHandleEntityNotFound() {
//        EntityNotFoundException ex = new EntityNotFoundException("Entity not found");
//
//        when(request.getRequestURI()).thenReturn("/product");
//
//        StandardError expectedError = new StandardError(Instant.now(),
//                HttpStatus.NOT_FOUND.value(),
//                "Entity not found",
//                ex.getMessage(),
//                "/product");
//
//        ResponseEntity<StandardError> response = handler.handleEntityNotFound(ex, request);
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//        assertThat(response.getBody().getError()).isEqualTo(expectedError.getError());
//        assertThat(response.getBody().getPath()).isEqualTo(expectedError.getPath());
//        assertThat(response.getBody().getMessage()).isEqualTo(expectedError.getMessage());
//        assertThat(response.getBody().getStatus()).isEqualTo(expectedError.getStatus());
//    }
//
//    @Test
//    public void testHandleIllegalState() throws Exception {
//
//        IllegalStateException exMock = new IllegalStateException("Test exception");
//
//        when(request.getRequestURI()).thenReturn("/product");
//
//        ResponseEntity<StandardError> response = handler.handleIllegalState(exMock, request);
//
//        Assert.assertNotNull(response);
//        assertEquals(BAD_REQUEST.value(), response.getStatusCodeValue());
//
//        StandardError error = response.getBody();
//        Assert.assertNotNull(error);
//        Assert.assertEquals(Instant.now().toEpochMilli(), error.getTimestamp().toEpochMilli(), 1000);
//        Assert.assertEquals(Optional.of(BAD_REQUEST.value()), Optional.of(error.getStatus()));
//        Assert.assertEquals("Illegal state", error.getError());
//        Assert.assertEquals("Test exception", error.getMessage());
//        Assert.assertEquals("/product", error.getPath());
//    }
//
//    @Test
//    public void testHandleIllegalArgument() throws Exception {
//
//        IllegalArgumentException exMock = new IllegalArgumentException("Invalid argument");
//
//        when(request.getRequestURI()).thenReturn("/product");
//
//        ResponseEntity<StandardError> response = handler.handleIllegalArgument(exMock, request);
//
//        Assert.assertNotNull(response);
//        assertEquals(BAD_REQUEST.value(), response.getStatusCodeValue());
//
//        StandardError error = response.getBody();
//        Assert.assertNotNull(error);
//        Assert.assertEquals(Instant.now().toEpochMilli(), error.getTimestamp().toEpochMilli(), 1000);
//        Assert.assertEquals(Optional.of(BAD_REQUEST.value()), Optional.of(error.getStatus()));
//        Assert.assertEquals("Illegal Argument", error.getError());
//        Assert.assertEquals("Invalid argument", error.getMessage());
//        Assert.assertEquals("/product", error.getPath());
//    }
//}
