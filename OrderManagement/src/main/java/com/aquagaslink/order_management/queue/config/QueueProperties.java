package com.aquagaslink.order_management.queue.config;

import org.springframework.stereotype.Component;

@Component
public class QueueProperties {

    private String appProductChannel = "orderToProductEventListener-out-0";
    private String appDeliveryChannel = "orderToDeliveryEventListener-out-0";
    private String appClientChannel = "orderToClientEventListener-out-0";


    public QueueProperties() {
    }

    public QueueProperties(String appProductChannel, String appDeliveryChannel, String appClientChannel) {
        this.appProductChannel = appProductChannel;
        this.appDeliveryChannel = appDeliveryChannel;
        this.appClientChannel = appClientChannel;
    }

    public String getAppProductChannel() {
        return appProductChannel;
    }

    public void setAppProductChannel(String appProductChannel) {
        this.appProductChannel = appProductChannel;
    }

    public String getAppDeliveryChannel() {
        return appDeliveryChannel;
    }

    public void setAppDeliveryChannel(String appDeliveryChannel) {
        this.appDeliveryChannel = appDeliveryChannel;
    }

    public String getAppClientChannel() {
        return appClientChannel;
    }

    public void setAppClientChannel(String appClientChannel) {
        this.appClientChannel = appClientChannel;
    }
}
