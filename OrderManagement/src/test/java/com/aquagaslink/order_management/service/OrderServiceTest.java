package com.aquagaslink.order_management.service;

import com.aquagaslink.order_management.controller.dto.OrderDto;
import com.aquagaslink.order_management.controller.dto.OrderFormDto;
import com.aquagaslink.order_management.helper.ClientOrderHelper;
import com.aquagaslink.order_management.infrastructure.ClientOrderRepository;
import com.aquagaslink.order_management.model.ClientOrder;
import com.aquagaslink.order_management.model.OrderStatus;
import com.aquagaslink.order_management.queue.OrderEventGateway;
import com.aquagaslink.order_management.queue.dto.client.ClientToOrderIn;
import com.aquagaslink.order_management.queue.dto.product.OrderToProductOut;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private ClientOrderRepository clientOrderRepository;

    private OrderService service;

    private ClientOrderHelper helper;

    private OrderEventGateway orderEventGateway;

    AutoCloseable mock;

    @BeforeEach
    void setup(){
        mock = MockitoAnnotations.openMocks(this);
        service = new OrderService(clientOrderRepository, null);
    };

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

//    @Test
//    void shouldCreateOrder(){
//        var code = "code";
//        var orderModel = helper.createClientOrder(code);
//        var order = helper.createOrderFormDto(code);
//
//        when(clientOrderRepository.save(any(ClientOrder.class)))
//                .thenReturn(orderModel);
//
//        var orderRead = service.createOrder(order);
//
//        assertThat(orderRead.clientName()).isEqualTo(order.clientName());
//        assertThat(orderRead.productCode()).isEqualTo(order.productCode());
//        assertThat(orderRead.status()).isEqualTo(order.status());
//        verify(clientOrderRepository, times(1)).save(any(ClientOrder.class));
//
//    }

    @Test
    void shouldFindOrderById() {
        UUID orderId = UUID.randomUUID();
        ClientOrder clientOrder = mock(ClientOrder.class);
        when(clientOrderRepository.findById(orderId)).thenReturn(Optional.of(clientOrder));
        when(clientOrder.getId()).thenReturn(orderId);

        OrderDto result = service.getOrderById(orderId);

        assertNotNull(result);
        assertEquals(orderId, result.id());
        verify(clientOrderRepository, times(1)).findById(orderId);
    }


    @Test
    void shouldListOrdersByClientId() {
        UUID clientId = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0, 10);
        ClientOrder order1 = mock(ClientOrder.class);
        ClientOrder order2 = mock(ClientOrder.class);
        Page<ClientOrder> page = new PageImpl<>(Arrays.asList(order1, order2), pageable, 2);

        when(clientOrderRepository.findAllByClientId(clientId, pageable)).thenReturn(page);

        Page<OrderDto> result = service.listOrdersByClientId(clientId, pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        verify(clientOrderRepository, times(1)).findAllByClientId(clientId, pageable);
    }

    @Test
    void shouldUpdateOrder() {
        UUID orderId = UUID.randomUUID();
        OrderFormDto formDto = new OrderFormDto(UUID.randomUUID(), 1, BigDecimal.ONE, UUID.randomUUID());
        ClientOrder clientOrder = mock(ClientOrder.class);

        when(clientOrderRepository.findById(orderId)).thenReturn(Optional.of(clientOrder));
        when(clientOrderRepository.save(clientOrder)).thenReturn(clientOrder);

        OrderDto result = service.updateOrder(orderId, formDto);

        assertNotNull(result);
        verify(clientOrderRepository, times(1)).findById(orderId);
        verify(clientOrderRepository, times(1)).save(clientOrder);
    }

    @Test
    void shouldUpdateOrderStatus() {
        UUID orderId = UUID.randomUUID();
        OrderStatus newStatus = OrderStatus.COMPLETED;
        ClientOrder clientOrder = mock(ClientOrder.class);

        when(clientOrderRepository.findById(orderId)).thenReturn(Optional.of(clientOrder));
        when(clientOrderRepository.save(clientOrder)).thenReturn(clientOrder);

        OrderDto result = service.updateOrderStatus(orderId, newStatus);

        assertNotNull(result);
        verify(clientOrderRepository, times(1)).findById(orderId);
        verify(clientOrderRepository, times(1)).save(clientOrder);
        verify(clientOrder).setStatus(newStatus);
    }

    //todo - testando o metodo de validacao de cliente
//    @Test
//    void shouldValidateClientWithError() {
//        UUID orderId = UUID.randomUUID();
//        UUID id = UUID.randomUUID();
//        ClientToOrderIn payload = new ClientToOrderIn(id, orderId, "Client name", "Client phone", null, true, "Client error");
//        ClientOrder clientOrder = mock(ClientOrder.class);
//
//        when(clientOrderRepository.findById(orderId)).thenReturn(Optional.of(clientOrder));
//
//        service.validateClient(payload);
//
//        verify(clientOrderRepository, times(1)).findById(orderId);
//        verify(clientOrder, times(1)).setHasClientError(true);
//        verify(clientOrder, times(1)).setObservation(anyString());
//        verify(clientOrder, times(1)).setStatus(OrderStatus.ERROR);
//        verify(clientOrder, times(1)).setUpdatedAt();
//        verify(clientOrderRepository, times(1)).saveAndFlush(clientOrder);
//    }

}
