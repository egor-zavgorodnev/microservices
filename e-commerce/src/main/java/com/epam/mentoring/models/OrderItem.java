package com.epam.mentoring.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    private Integer id;
    private String price;

    private Order order;
    private long productId;
    private long groupVariant;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @JoinColumn(name = "product_id")
    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    @JoinColumn(name = "product_variant_id")
    public long getGroupVariant() {
        return groupVariant;
    }

    public void setGroupVariant(long groupVariant) {
        this.groupVariant = groupVariant;
    }
}
