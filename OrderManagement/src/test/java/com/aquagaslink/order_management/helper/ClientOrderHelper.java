package com.aquagaslink.order_management.helper;

import com.aquagaslink.order_management.controller.dto.OrderFormDto;
import com.aquagaslink.order_management.model.ClientOrder;
import com.aquagaslink.order_management.model.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ClientOrderHelper {

    public static ClientOrder createClientOrder(String code){
        return new ClientOrder(code, LocalDateTime.now(),LocalDateTime.now(),OrderStatus.CREATED,"code","nome",UUID.randomUUID());
    }


    public static OrderFormDto createOrderFormDto(String code){
        return new OrderFormDto(code, OrderStatus.CREATED,"code","nome",UUID.randomUUID());

    }

    public static OrderFormDto createOrderFormDto(){
        return new OrderFormDto("code",OrderStatus.CANCELLED,"code","nome",UUID.randomUUID());

    }

}
