package com.porejemplo.task_four.persist;

import java.io.Serializable;
import java.util.Objects;

public class BuyerProductId implements Serializable {

    private Long buyerId;

    private Long productId;

    public BuyerProductId() {
    }

    public BuyerProductId(Long buyerId, Long productId) {
        this.buyerId = buyerId;
        this.productId = productId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuyerProductId that = (BuyerProductId) o;
        return buyerId == that.getBuyerId() && productId == that.getProductId();
    }

    @Override
    public int hashCode() {
        return buyerId.hashCode() + productId.hashCode();
    }
}
