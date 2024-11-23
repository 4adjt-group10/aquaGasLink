package com.aquagaslink.product.queue.config;

import org.springframework.stereotype.Component;

@Component
public class ProductProperties {
    private String appProductChannel = "productEventListener-out-0";
    private String appClientChannel = "clientEventListener-out-0";
    private String appOrderChannel = "orderEventListener-out-0";

    public ProductProperties() {
    }

    public ProductProperties(String appProductChannel, String appClientChannel) {
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

    public String getAppOrderChannel() {
        return appOrderChannel;
    }

    public void setAppOrderChannel(String appOrderChannel) {
        this.appOrderChannel = appOrderChannel;
    }
}
