package com.aquagaslink.delivery.queue.config;

import org.springframework.stereotype.Component;

@Component
public class QueueProperties {

    private String appOrderChannel = "deliveryToOrderEventListener-out-0";

    @Deprecated(since = "Only for framework use")
    public QueueProperties() {
    }

    public QueueProperties(String appOrderChannel) {
        this.appOrderChannel = appOrderChannel;
    }

    public String getAppOrderChannel() {
        return appOrderChannel;
    }

    public void setAppOrderChannel(String appOrderChannel) {
        this.appOrderChannel = appOrderChannel;
    }
}
