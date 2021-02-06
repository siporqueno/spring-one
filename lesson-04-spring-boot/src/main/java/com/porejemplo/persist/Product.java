package com.porejemplo.persist;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

public class Product {

    private Long id;

    @NotEmpty
    private String title;

    private String description;

    @PositiveOrZero
    private int price;

    public Product(String title, int price) {
        this.id = -1L;
        this.title = title;
        this.price = price;
    }

    public Product(String title, String description, int price) {
        this.id = -1L;
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public Product() {
        this.id = -1L;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
