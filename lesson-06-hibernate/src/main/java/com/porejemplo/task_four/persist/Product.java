package com.porejemplo.task_four.persist;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
@NamedQueries({
        @NamedQuery(name = "productByIdWithBuyerProducts", query = "select p from Product p left join fetch p.buyerProducts bp where p.id=:id")
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128, nullable = false)
    private String title;

    @Column(length = 16, nullable = false)
    private int price;

//    @ManyToMany
//    @JoinTable(name = "buyers_products",
//            joinColumns = @JoinColumn(name = "product_id"),
//            inverseJoinColumns = @JoinColumn(name = "buyer_id"))
//    private List<Buyer> buyers;

    @OneToMany(mappedBy = "product", orphanRemoval = true)
    private List<BuyerProduct> buyerProducts;

    public Product() {
    }

    public Product(String title, int price) {
        this.title = title;
        this.price = price;
    }

    //    public Product(String title, int price, List<Buyer> buyers) {
//        this.title = title;
//        this.price = price;
//        this.buyers = buyers;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

//    public List<Buyer> getBuyers() {
//        return buyers;
//    }
//
//    public void setBuyers(List<Buyer> buyers) {
//        this.buyers = buyers;
//    }


    public List<BuyerProduct> getBuyerProducts() {
        return buyerProducts;
    }

    public void setBuyerProducts(List<BuyerProduct> buyerProducts) {
        this.buyerProducts = buyerProducts;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
