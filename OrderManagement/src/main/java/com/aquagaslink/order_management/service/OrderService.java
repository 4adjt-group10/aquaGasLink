package com.aquagaslink.order_management.service;

import com.aquagaslink.order_management.controller.dto.OrderDto;
import com.aquagaslink.order_management.controller.dto.OrderFormDto;
import com.aquagaslink.order_management.infrastructure.ClientOrderRepository;
import com.aquagaslink.order_management.model.ClientOrder;
import com.aquagaslink.order_management.model.OrderStatus;
import com.aquagaslink.order_management.queue.producer.MessageProducer;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    private static final String ORDER_NOT_FOUND = "Order not found";

    private final ClientOrderRepository clientOrderRepository;
    private final MessageProducer messageProducer;

    public OrderService(ClientOrderRepository clientOrderRepository, MessageProducer messageProducer) {
        this.clientOrderRepository = clientOrderRepository;
        this.messageProducer = messageProducer;
    }

    public OrderDto createOrder(OrderFormDto formDto) {
        ClientOrder newClientOrder = clientOrderRepository.save(new ClientOrder(formDto));
        return new OrderDto(newClientOrder);
    }

    public OrderDto getOrderById(UUID id) {
        return clientOrderRepository.findById(id).map(OrderDto::new).orElseThrow(() -> new EntityNotFoundException(ORDER_NOT_FOUND));
    }

    public Page<OrderDto> listOrdersByClientId(UUID clientId, Pageable pageable) {
        return clientOrderRepository.findAllByClientId(clientId, pageable).map(OrderDto::new);
    }

    public OrderDto updateOrder(UUID id, OrderFormDto formDto) {
        ClientOrder clientOrder = clientOrderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ORDER_NOT_FOUND));
        clientOrder.merge(formDto);
        return new OrderDto(clientOrderRepository.save(clientOrder));
    }

    public OrderDto updateOrderStatus(UUID id, OrderStatus status) {
        ClientOrder clientOrder = clientOrderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ORDER_NOT_FOUND));
        clientOrder.setStatus(status);
        return new OrderDto(clientOrderRepository.save(clientOrder));
    }

    public void sendProduct(String message) {
        messageProducer.sendProduct(message);
    }

    public void sendDelivery(String message) {
        messageProducer.sendDelivery(message);
    }
}
