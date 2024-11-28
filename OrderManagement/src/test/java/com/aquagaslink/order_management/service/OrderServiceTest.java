package com.aquagaslink.order_management.service;

import com.aquagaslink.order_management.controller.dto.OrderDto;
import com.aquagaslink.order_management.controller.dto.OrderFormDto;
import com.aquagaslink.order_management.helper.ClientOrderHelper;
import com.aquagaslink.order_management.infrastructure.ClientOrderRepository;
import com.aquagaslink.order_management.model.ClientOrder;
import com.aquagaslink.order_management.model.OrderStatus;
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

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private ClientOrderRepository clientOrderRepository;

    private OrderService service;

    private ClientOrderHelper helper;

    AutoCloseable mock;

    @BeforeEach
    void setup(){
        mock = MockitoAnnotations.openMocks(this);
        service = new OrderService(clientOrderRepository);
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

    @Test
    public void testListOrdersByClientId() {
        UUID clientId = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0, 10);
        var order1 = helper.createClientOrderIdClient(clientId);
        var order2 = helper.createClientOrderIdClient(clientId);

        Page<ClientOrder> page = new PageImpl<>(Arrays.asList(order1, order2), pageable, 20);

        when(clientOrderRepository.findAllByClientId(clientId, pageable)).thenReturn(page);
        Page<OrderDto> result = service.listOrdersByClientId(clientId, pageable);

        verify(clientOrderRepository).findAllByClientId(clientId, pageable);
        System.out.println(result.get().toList());
        Assertions.assertEquals(2, result.getNumberOfElements());
        Assertions.assertEquals(2, result.getContent().size());
        // ... other assertions on the content of the OrderDto objects
    }

    @Test
    public void testConstructor() {
        Assertions.assertNotNull(service);

    }

}
