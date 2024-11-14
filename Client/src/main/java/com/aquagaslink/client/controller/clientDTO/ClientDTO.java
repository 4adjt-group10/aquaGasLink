package com.aquagaslink.client.controller.clientDTO;

import com.aquagaslink.client.model.ClientModel;

import java.util.UUID;

public record ClientDTO(
        UUID id,
        String cpf,
        String name,
        String email,
        String phone,
        String address,
        String city,
        String state,
        String number,
        String country
) {

    public ClientDTO(ClientModel client) {
        this(client.getId(),
                client.getCpf(),
                client.getName(),
                client.getEmail(),
                client.getPhone(),
                client.getAddress(),
                client.getCity(),
                client.getState(),
                client.getNumber(),
                client.getCountry());

    }


}
