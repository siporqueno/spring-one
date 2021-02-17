package com.porejemplo.task_four.persist;

import javax.persistence.*;

@Entity
@Table(name = "buyers_products")
@IdClass(BuyerProductId.class)
@NamedQueries({
        @NamedQuery(name = "allBuyerProducts", query = "from BuyerProduct")
})
public class BuyerProduct {

    @Id
    @Column(name = "buyer_id")
    private Long buyerId;

    @ManyToOne
    @JoinColumn(name = "buyer_id", insertable = false, updatable = false)
    private Buyer buyer;

    @Id
    @Column(name = "product_id")
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @Column(name = "price_paid")
    private int pricePaid;

    public BuyerProduct() {
    }

    public BuyerProduct(Buyer buyer, Product product) {
        this.buyer = buyer;
        this.buyerId = buyer.getId();
        this.product = product;
        this.productId = product.getId();
        this.pricePaid = product.getPrice();
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getPricePaid() {
        return pricePaid;
    }

    public void setPricePaid(int pricePaid) {
        this.pricePaid = pricePaid;
    }
}
