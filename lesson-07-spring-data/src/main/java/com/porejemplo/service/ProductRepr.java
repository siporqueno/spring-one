package com.porejemplo.service;

import com.porejemplo.persist.Product;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class ProductRepr {

    private Long id;

    @NotEmpty
    private String title;

    private String description;

    @PositiveOrZero
    private BigDecimal price;

    public ProductRepr() {
    }

    public ProductRepr(String title, String description, BigDecimal price) {
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public ProductRepr(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.description = product.getDescription();
        this.price = product.getPrice();
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
