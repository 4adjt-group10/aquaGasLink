package com.aquagaslink.client.model;

import com.aquagaslink.client.controller.clientDTO.ClientDTOForm;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class ClientModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private String cpf;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String number;
    private String country;

    public ClientModel() {
    }

    public ClientModel(String cpf, String name, String email, String phone, String address, String city, String state, String number, String country) {
        this.cpf = cpf;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.number = number;
        this.country = country;
    }

    public ClientModel(ClientDTOForm clientDTOForm) {
        this.cpf = clientDTOForm.cpf();
        this.name = clientDTOForm.name();
        this.email = clientDTOForm.email();
        this.phone = clientDTOForm.phone();
        this.address = clientDTOForm.address();
        this.city = clientDTOForm.city();
        this.state = clientDTOForm.state();
        this.number = clientDTOForm.number();
        this.country = clientDTOForm.country();
    }

    public void updateFromDTO(ClientDTOForm clientDTOForm) {
        this.cpf = clientDTOForm.cpf();
        this.name = clientDTOForm.name();
        this.email = clientDTOForm.email();
        this.phone = clientDTOForm.phone();
        this.address = clientDTOForm.address();
        this.city = clientDTOForm.city();
        this.state = clientDTOForm.state();
        this.number = clientDTOForm.number();
        this.country = clientDTOForm.country();
    }

    public UUID getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getNumber() {
        return number;
    }

    public String getCountry() {
        return country;
    }
}
