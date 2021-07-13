package com.epam.statistic.model;

import javax.persistence.*;

@Entity
@Table(name = "statistic")
public class StatisticRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
