package com.aquagaslink.client.model;

public class ClientAddress {

    private String postalCode;
    private String street;
    private String clientCity;
    private String clientState;
    private String clientNumber;
    private String clientCountry;

    @Deprecated
    public ClientAddress() {
    }

    public ClientAddress(String postalCode,
                         String street,
                         String clientCity,
                         String clientState,
                         String clientNumber,
                         String clientCountry) {
        this.postalCode = postalCode;
        this.street = street;
        this.clientCity = clientCity;
        this.clientState = clientState;
        this.clientNumber = clientNumber;
        this.clientCountry = clientCountry;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getStreet() {
        return street;
    }

    public String getClientCity() {
        return clientCity;
    }

    public String getClientState() {
        return clientState;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public String getClientCountry() {
        return clientCountry;
    }
}
