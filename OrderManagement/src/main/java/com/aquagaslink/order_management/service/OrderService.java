package com.aquagaslink.order_management.service;

import com.aquagaslink.order_management.controller.dto.OrderDto;
import com.aquagaslink.order_management.controller.dto.OrderFormDto;
import com.aquagaslink.order_management.infrastructure.ClientOrderRepository;
import com.aquagaslink.order_management.model.ClientOrder;
import com.aquagaslink.order_management.model.OrderStatus;
import com.aquagaslink.order_management.queue.OrderEventGateway;
import com.aquagaslink.order_management.queue.dto.client.ClientToOrderIn;
import com.aquagaslink.order_management.queue.dto.client.OrderToClientOut;
import com.aquagaslink.order_management.queue.dto.delivery.OrderToDeliveryOut;
import com.aquagaslink.order_management.queue.dto.product.OrderToProductOut;
import com.aquagaslink.order_management.queue.dto.product.ProductToOrderIn;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class OrderService {

    private static final String ORDER_NOT_FOUND = "Order not found";

    Logger logger = Logger.getLogger(OrderService.class.getName());

    private final ClientOrderRepository clientOrderRepository;
    private final OrderEventGateway orderEventGateway;

    public OrderService(ClientOrderRepository clientOrderRepository, OrderEventGateway orderEventGateway) {
        this.clientOrderRepository = clientOrderRepository;
        this.orderEventGateway = orderEventGateway;
    }

    public OrderDto createOrder(OrderFormDto formDto) {
        ClientOrder newClientOrder = clientOrderRepository.save(new ClientOrder(formDto));
        orderEventGateway.sendClientEvent(new OrderToClientOut(formDto.clientId(), newClientOrder.getId()));
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
        clientOrder.setUpdatedAt();
        return new OrderDto(clientOrderRepository.save(clientOrder));
    }

    public void validateClient(ClientToOrderIn payload) {
        Boolean hasError = payload.hasError();
        Optional<ClientOrder> clientOrder = clientOrderRepository.findById(payload.orderId());
        if(hasError) {
            clientOrder.ifPresentOrElse(order -> {
                order.setHasClientError(true);
                order.setObservation(order.getObservation().concat("Client error: " + payload.observation()));
                order.setStatus(OrderStatus.ERROR);
                order.setUpdatedAt();
                clientOrderRepository.saveAndFlush(order);
            }, () -> {
                logger.severe("Order not found: " + payload.orderId());
            });
        } else {
            clientOrder.ifPresentOrElse(order -> {
                order.setClientAddress(payload.address());
                order.setHasClientError(false);
                order.setUpdatedAt();
                clientOrderRepository.saveAndFlush(order);
                orderEventGateway.sendProductEvent(new OrderToProductOut(order.getProductId(), order.getQuantity(), order.getId(), payload.name()));
            }, () -> {
                logger.severe("Order not found: " + payload.orderId());
            });
        }
    }

    public void validateProduct(ProductToOrderIn payload) {
        Boolean hasError = payload.hasError();
        Optional<ClientOrder> clientOrder = clientOrderRepository.findById(payload.orderId());
        if(hasError) {
            clientOrder.ifPresentOrElse(order -> {
                order.setHasProductError(true);
                order.setObservation(order.getObservation().concat("Product error: " + payload.observation()));
                order.setStatus(OrderStatus.ERROR);
                order.setUpdatedAt();
                clientOrderRepository.saveAndFlush(order);
            }, () -> {
                logger.severe("Order not found: " + payload.orderId());
            });
        } else {
            clientOrder.ifPresentOrElse(order -> {
                order.setHasProductError(false);
                order.setUpdatedAt();
                clientOrderRepository.saveAndFlush(order);
                validateOrderToDelivery(order, payload.productName(), payload.clientName());
            }, () -> {
                logger.severe("Order not found: " + payload.orderId());
            });
        }
    }

    public void validateOrderToDelivery(ClientOrder clientOrder, String productName, String clientName) {
        clientOrder.setStatus(OrderStatus.IN_PROGRESS);
        clientOrder.setUpdatedAt();
        clientOrderRepository.saveAndFlush(clientOrder);
        orderEventGateway.sendDeliveryEvent(new OrderToDeliveryOut(clientOrder.getId(),
                clientOrder.getClientId(),
                clientName,
                clientOrder.getClientAddress(),
                productName,
                clientOrder.getPrice()));
    }
}
