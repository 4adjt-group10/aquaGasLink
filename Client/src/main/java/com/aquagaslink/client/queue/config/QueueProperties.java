package com.aquagaslink.client.queue.config;

import org.springframework.stereotype.Component;

@Component
public class QueueProperties {

    private String appOrderChannel = "clientToOrderEventListener-out-0";

    @Deprecated(since = "Only for framework use")
    public QueueProperties() {
    }

    public QueueProperties(String appOrderChannel) {
        this.appOrderChannel = appOrderChannel;
    }

    public String getAppClientChannel() {
        return appOrderChannel;
    }

    public void setAppClientChannel(String appOrderChannel) {
        this.appOrderChannel = appOrderChannel;
    }
}
