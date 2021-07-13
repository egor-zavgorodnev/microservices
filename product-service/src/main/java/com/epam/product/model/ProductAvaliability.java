package com.epam.product.model;

public class ProductAvaliability {
    private long creationTime;
    private long productId;
    private boolean isProductAvailable;

    public ProductAvaliability(long productId, boolean isProductAvailable) {
        this.creationTime = System.currentTimeMillis();
        this.productId = productId;
        this.isProductAvailable = isProductAvailable;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getProductId() {
        return productId;
    }

    public boolean isProductAvailable() {
        return isProductAvailable;
    }
}

