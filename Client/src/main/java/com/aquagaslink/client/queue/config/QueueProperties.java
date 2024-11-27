package com.aquagaslink.client.queue.config;

import org.springframework.stereotype.Component;

@Component
public class QueueProperties {

    private String appProductChannel = "clientToOrderEventListener-out-0";



    public QueueProperties() {
    }

    public QueueProperties(String appProductChannel, String appDeliveryChannel, String appClientChannel) {
        this.appProductChannel = appProductChannel;

    }

    public String getAppClientChannel() {
        return appProductChannel;
    }

    public void setAppProductChannel(String appProductChannel) {
        this.appProductChannel = appProductChannel;
    }

}
