package com.porejemplo.persist;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@NamedQueries({
        @NamedQuery(name = "productByIdWithBuyers", query = "select p from Product p left join fetch p.buyers b where p.id=:id")
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128, nullable = false)
    private String title;

    @Column(length = 16, nullable = false)
    private int price;

    @ManyToMany
    @JoinTable(name = "buyers_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "buyer_id"))
    private List<Buyer> buyers;

    public Product() {
    }

    public Product(String title, int price, List<Buyer> buyers) {
        this.title = title;
        this.price = price;
        this.buyers = buyers;
    }

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

    public List<Buyer> getBuyers() {
        return buyers;
    }

    public void setBuyers(List<Buyer> buyers) {
        this.buyers = buyers;
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
