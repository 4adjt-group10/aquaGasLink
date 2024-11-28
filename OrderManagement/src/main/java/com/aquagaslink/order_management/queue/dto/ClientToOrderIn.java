package com.aquagaslink.order_management.queue.dto;

import java.util.UUID;

public record ClientToOrderIn(UUID id,
                              String cpf,
                              String name,
                              String email,
                              String phone,
                              String address,
                              String city,
                              String state,
                              String number,
                              String country) {
}
