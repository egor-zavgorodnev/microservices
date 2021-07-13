package com.epam.kafka.dto;

public class StatisticRecord {

    private Long id;

    private String productId;

    private String username;

    public StatisticRecord() {
    }

    public StatisticRecord(String productId, String username) {
        this.productId = productId;
        this.username = username;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}