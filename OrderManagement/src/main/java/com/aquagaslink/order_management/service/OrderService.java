package com.aquagaslink.order_management.service;

import com.aquagaslink.order_management.controller.dto.OrderDto;
import com.aquagaslink.order_management.controller.dto.OrderFormDto;
import com.aquagaslink.order_management.infrastructure.ClientOrderRepository;
import com.aquagaslink.order_management.model.ClientOrder;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final ClientOrderRepository clientOrderRepository;

    public OrderService(ClientOrderRepository clientOrderRepository) {
        this.clientOrderRepository = clientOrderRepository;
    }

    public OrderDto createOrder(OrderFormDto formDto) {
        ClientOrder newClientOrder = clientOrderRepository.save(new ClientOrder(formDto));
        return new OrderDto(newClientOrder);
    }

    public OrderDto getOrderById(UUID id) {
        return clientOrderRepository.findById(id).map(OrderDto::new).orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    public List<OrderDto> listOrdersByClientId(UUID clientId) {
        return clientOrderRepository.findAllByClientId(clientId).stream().map(OrderDto::new).toList();
    }

    public OrderDto updateOrder(UUID id, OrderFormDto formDto) {
        ClientOrder clientOrder = clientOrderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found"));
        clientOrder.merge(formDto);
        return new OrderDto(clientOrderRepository.save(clientOrder));
    }
}
