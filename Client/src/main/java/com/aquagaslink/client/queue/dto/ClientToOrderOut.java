package com.aquagaslink.client.queue.dto;

import java.util.UUID;

public record ClientToOrderOut(UUID id,
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
