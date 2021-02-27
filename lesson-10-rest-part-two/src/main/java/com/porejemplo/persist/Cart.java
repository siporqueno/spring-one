package com.porejemplo.persist;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private Long id;

    private User user;

    private List<Product> products = new ArrayList<>();

    public Cart(User user) {
        this.user = user;
        this.id = user.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
