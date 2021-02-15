package com.porejemplo.task_four.service;

import com.porejemplo.task_four.persist.*;

import java.util.List;
import java.util.stream.Collectors;

public class InfoService {
    private BuyerRepository buyerRepository;
    private ProductRepository productRepository;

    public InfoService(BuyerRepository buyerRepository, ProductRepository productRepository) {
        this.buyerRepository = buyerRepository;
        this.productRepository = productRepository;
    }

    public List<Product> findProductsOfBuyerWithId(long id) {
        return buyerRepository.findByIdWithBuyerProducts(id).getBuyerProducts().stream().map(bp -> bp.getProduct()).collect(Collectors.toList());
    }

    public List<BuyerProduct> findBuyerProductsOfBuyerWithId(long id) {
        return buyerRepository.findByIdWithBuyerProducts(id).getBuyerProducts();
    }

    public List<Buyer> findBuyersOfProductWithId(long id) {
        return productRepository.findByIdWithBuyerProducts(id).getBuyerProducts().stream().map(bp -> bp.getBuyer()).collect(Collectors.toList());
    }

}
