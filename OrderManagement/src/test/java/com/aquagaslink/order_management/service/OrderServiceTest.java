package com.aquagaslink.order_management.service;

import com.aquagaslink.order_management.controller.dto.OrderFormDto;
import com.aquagaslink.order_management.helper.ClientOrderHelper;
import com.aquagaslink.order_management.infrastructure.ClientOrderRepository;
import com.aquagaslink.order_management.model.ClientOrder;
import com.aquagaslink.order_management.queue.producer.MessageProducer;
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
        var orderModel = helper.createClientOrder(code);
        System.out.println(orderModel.getId());
        var id = orderModel.getId();

        when(clientOrderRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(orderModel));


        var orderRead = service.getOrderById(id);

        //System.out.println(productRead.productCode());
        assertThat(orderRead.id()).isEqualTo(orderModel.getId());
        assertThat(orderRead.code()).isEqualTo(orderModel.getCode());
        assertThat(orderRead.status()).isEqualTo(orderModel.getStatus());
        assertThat(orderRead.clientName()).isEqualTo(orderModel.getClientName());
        assertThat(orderRead.productCode()).isEqualTo(orderModel.getProductCode());
        verify(clientOrderRepository, times(1)).findById(any(UUID.class));
    }
}
