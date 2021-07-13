package com.epam.product.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("storage")
@Component
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "uploads";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
