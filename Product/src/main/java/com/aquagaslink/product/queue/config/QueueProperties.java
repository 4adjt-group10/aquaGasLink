package com.aquagaslink.product.queue.config;

import org.springframework.stereotype.Component;

@Component
public class QueueProperties {

    private String appOrderChannel = "productToOrderEventListener-out-0";
    private String appClientChannel = "productToClientEventListener-out-0";
    private String appDeliveryChannel = "productToDeliveryEventListener-out-0";

    @Deprecated(since = "Only for framework")
    public QueueProperties() {
    }

    public QueueProperties(String appOrderChannel, String appClientChannel, String appDeliveryChannel) {
        this.appOrderChannel = appOrderChannel;
        this.appClientChannel = appClientChannel;
        this.appDeliveryChannel = appDeliveryChannel;
    }

    public String getAppOrderChannel() {
        return appOrderChannel;
    }

    public void setAppOrderChannel(String appOrderChannel) {
        this.appOrderChannel = appOrderChannel;
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

    public void setAppDeliveryChannel(String appDeliveryChannel) {
        this.appDeliveryChannel = appDeliveryChannel;
    }
}
