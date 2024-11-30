package com.aquagaslink.order_management.model;

public class ClientAddress {

    private String clientName;
    private String clientPhone;
    private String clientCity;
    private String clientState;
    private String clientNumber;
    private String clientCountry;

    public ClientAddress(String clientName,
                         String clientPhone,
                         String clientCity,
                         String clientState,
                         String clientNumber,
                         String clientCountry) {
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.clientCity = clientCity;
        this.clientState = clientState;
        this.clientNumber = clientNumber;
        this.clientCountry = clientCountry;
    }

    public String getClientName() {
        return clientName;
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
