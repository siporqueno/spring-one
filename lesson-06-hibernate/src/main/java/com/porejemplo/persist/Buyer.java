package com.porejemplo.persist;

import javax.persistence.*;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "buyers")
@NamedQueries({
        @NamedQuery(name = "buyerByName", query = "from Buyer b where b.name=:name"),
        @NamedQuery(name = "allBuyers", query = "from Buyer"),
        @NamedQuery(name = "allBuyersWithProducts", query = "select b from Buyer b left join fetch b.products p"),
        @NamedQuery(name = "buyerByIdWithProducts", query = "select b from Buyer b left join fetch b.products p where b.id=:id")
})
public class Buyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128, unique = true, nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "buyers_products",
            joinColumns = @JoinColumn(name = "buyer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    public Buyer() {
    }

    public Buyer(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
