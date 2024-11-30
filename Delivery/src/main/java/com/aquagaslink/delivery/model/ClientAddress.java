package com.aquagaslink.delivery.model;

public class ClientAddress {

    private String postalCode;
    private String street;
    private String clientPhone;
    private String clientCity;
    private String clientState;
    private String clientNumber;
    private String clientCountry;

    @Deprecated
    public ClientAddress() {
    }

    public ClientAddress(String postalCode,
                         String street,
                         String clientPhone,
                         String clientCity,
                         String clientState,
                         String clientNumber,
                         String clientCountry) {
        this.postalCode = postalCode;
        this.street = street;
        this.clientPhone = clientPhone;
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

    public String getClientPhone() {
        return clientPhone;
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
