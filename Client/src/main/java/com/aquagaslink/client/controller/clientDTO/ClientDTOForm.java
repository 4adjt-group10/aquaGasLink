package com.aquagaslink.client.controller.clientDTO;

import com.aquagaslink.client.model.ClientModel;

public record ClientDTOForm(
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

    public ClientDTOForm(ClientModel client) {
        this(   client.getCpf(),
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