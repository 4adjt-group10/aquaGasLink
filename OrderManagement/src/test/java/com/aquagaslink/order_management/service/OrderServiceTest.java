package com.aquagaslink.order_management.service;

import com.aquagaslink.order_management.controller.dto.OrderDto;
import com.aquagaslink.order_management.controller.dto.OrderFormDto;
import com.aquagaslink.order_management.helper.ClientOrderHelper;
import com.aquagaslink.order_management.infrastructure.ClientOrderRepository;
import com.aquagaslink.order_management.model.ClientOrder;
import com.aquagaslink.order_management.model.OrderStatus;
import com.aquagaslink.order_management.queue.producer.MessageProducer;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private ClientOrderRepository clientOrderRepository;

    @Mock
    private MessageProducer messageProducer;

    private OrderService service;

    @Autowired
    private ClientOrderHelper helper;

    AutoCloseable mock;

    @BeforeEach
    void setup(){
        mock = MockitoAnnotations.openMocks(this);
        service = new OrderService(clientOrderRepository, messageProducer);
    };

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void shouldCreateOrder(){
        var code = "code";
        var orderModel = helper.createClientOrder(code);
        var order = helper.createOrderFormDto(code);

        when(clientOrderRepository.save(any(ClientOrder.class)))
                .thenReturn(orderModel);

        var orderRead = service.createOrder(order);

        //System.out.println(productRead.productCode());
        assertThat(orderRead.clientName()).isEqualTo(order.clientName());
        assertThat(orderRead.productCode()).isEqualTo(order.productCode());
        assertThat(orderRead.status()).isEqualTo(order.status());
        verify(clientOrderRepository, times(1)).save(any(ClientOrder.class));

    }

    @Test
    void shouldFindOrderById(){
        var code = "code";
        ClientOrder existingOrder = helper.createClientOrder(code);
        OrderFormDto updateForm = helper.createOrderFormDto(); // Assuming OrderFormDto has update fields
        UUID orderId = existingOrder.getId();

        when(clientOrderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));

        var orderRead = service.getOrderById(orderId);

        //System.out.println(productRead.productCode());
        assertThat(orderRead.id()).isEqualTo(existingOrder.getId());
        assertThat(orderRead.code()).isEqualTo(existingOrder.getCode());
        assertThat(orderRead.status()).isEqualTo(existingOrder.getStatus());
        assertThat(orderRead.clientName()).isEqualTo(existingOrder.getClientName());
        assertThat(orderRead.productCode()).isEqualTo(existingOrder.getProductCode());
    }

    @Test
    public void shouldUpdateOrder() throws Exception {
        var code = "code";
        ClientOrder existingOrder = helper.createClientOrder(code);
        OrderFormDto updateForm = helper.createOrderFormDto(); // Assuming OrderFormDto has update fields
        UUID orderId = existingOrder.getId();

        when(clientOrderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));
        OrderDto updatedOrder = new OrderDto(existingOrder); // Mock updated OrderDto

        when(clientOrderRepository.save(existingOrder)).thenReturn(existingOrder);

        OrderDto result = service.updateOrder(orderId, updateForm);

        Assert.assertNotEquals(updatedOrder.status(), result.status());
        verify(clientOrderRepository).findById(orderId);
        verify(clientOrderRepository).save(existingOrder);
    }

    @Test
    public void shouldUpdateOrderWithStatus() throws Exception {
        var code = "code";
        ClientOrder existingOrder = helper.createClientOrder(code);
        OrderFormDto updateForm = helper.createOrderFormDto(); // Assuming OrderFormDto has update fields
        UUID orderId = existingOrder.getId();
        OrderStatus newStatus = OrderStatus.COMPLETED; // Change this to your desired status

        when(clientOrderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));
        OrderDto updatedOrder = new OrderDto(existingOrder); // Mock updated OrderDto

        when(clientOrderRepository.save(existingOrder)).thenReturn(existingOrder);

        OrderDto result = service.updateOrderStatus(orderId, newStatus);

        assertEquals(updatedOrder.code(), result.code());
        verify(clientOrderRepository).findById(orderId);
        verify(clientOrderRepository).save(existingOrder);
    }

}
