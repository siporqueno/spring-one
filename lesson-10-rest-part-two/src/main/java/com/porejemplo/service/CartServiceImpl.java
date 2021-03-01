package com.porejemplo.service;

import com.porejemplo.controller.NotFoundException;
import com.porejemplo.persist.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ProductRepr> findAllProductsOfUserWithId(long userId) {
        Cart cart = cartRepository.findById(userId);
        if (cart == null) {
            cartRepository.save(new Cart(userRepository.findById(userId).orElseThrow(() -> new NotFoundException())));
        }

        return cartRepository.findById(userId).getProducts().stream()
                .map(ProductRepr::new)
                .collect(Collectors.toList());
    }

    @Override
    public void addProductByIdToUserWithId(long productId, long userId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException());
        cartRepository.findById(userId).getProducts().add(product);
    }

    @Override
    public void deleteProductByIdFromUserWithId(long productId, long userId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException());
        cartRepository.findById(userId).getProducts().remove(product);
    }

    @Override
    public void deleteAllProductsFromUserWithId(long userId) {
        cartRepository.findById(userId).getProducts().clear();
    }
}
