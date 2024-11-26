package com.aquagaslink.order_management.queue.config;

import org.springframework.stereotype.Component;

@Component
public class QueueProperties {

    private String appProductChannel = "orderToProductEventListener-out-0";
    private String appClientChannel = "clientEventListener-out-0";
    private String appDeliveryChannel = "deliveryEventListener-out-0";

    public QueueProperties() {
    }

    public QueueProperties(String appProductChannel, String appClientChannel) {
        this.appProductChannel = appProductChannel;
        this.appClientChannel = appClientChannel;
    }

    public String getAppProductChannel() {
        return appProductChannel;
    }

    public void setAppProductChannel(String appProductChannel) {
        this.appProductChannel = appProductChannel;
    }

    public String getAppClientChannel() {
        return appClientChannel;
    }

    public void setAppClientChannel(String appClientChannel) {
        this.appClientChannel = appClientChannel;
    }

    public String getAppDeliveryChannel() {
        return appDeliveryChannel;
    }

    public void setAppDeliveryChannel(String appOrderChannel) {
        this.appDeliveryChannel = appOrderChannel;
    }
}
