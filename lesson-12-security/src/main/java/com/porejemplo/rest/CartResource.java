package com.porejemplo.rest;

import com.porejemplo.service.CartService;
import com.porejemplo.service.ProductRepr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartResource {
    private final CartService cartService;

    @Autowired
    public CartResource(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{user_id}")
    public List<ProductRepr> findAllByUserId(@PathVariable("user_id") Long userId) {
        return cartService.findAllProductsOfUserWithId(userId);
    }

    @PostMapping("/add/{user_id}/user/{product_id}/product")
    public void addProductByIdToUserWithId(@PathVariable("user_id") Long userId, @PathVariable("product_id") Long productId) {
        cartService.addProductByIdToUserWithId(productId, userId);
    }

    @PostMapping("/drop/{user_id}/user/{product_id}/product")
    public void deleteProductByIdFromUserWithId(@PathVariable("user_id") Long userId, @PathVariable("product_id") Long productId) {
        cartService.deleteProductByIdFromUserWithId(productId, userId);
    }

    @DeleteMapping("/{user_id}")
    public void deleteAllProductsFromUserWithId(@PathVariable("user_id") Long userId) {
        cartService.deleteAllProductsFromUserWithId(userId);
    }
}
