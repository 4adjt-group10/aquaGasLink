//package com.aquagaslink.order_management.controller;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//import com.aquagaslink.order_management.service.OrderService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//@WebMvcTest(OrderController.class)
//public class OrderControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private OrderService orderService;
//
//    @Test
//    public void testSendProductMessage() throws Exception {
//        String message = "Test message";
//
//        // Mock the orderService methods
//        doNothing().when(orderService).sendProduct(anyString());
//        doNothing().when(orderService).sendDelivery(anyString());
//
//        // Perform the POST request
//        mockMvc.perform(post("/send-product")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(message))
//                .andExpect(status().isOk());
//
//        // Verify the orderService methods were called with the correct message
//        verify(orderService, times(1)).sendProduct(message);
//        verify(orderService, times(1)).sendDelivery(message);
//    }
//
//}
