package com.porejemplo.service;

import com.porejemplo.persist.Buyer;
import com.porejemplo.persist.BuyerRepository;
import com.porejemplo.persist.Product;
import com.porejemplo.persist.ProductRepository;

import java.util.List;

public class InfoService {
    private BuyerRepository buyerRepository;
    private ProductRepository productRepository;

    public InfoService(BuyerRepository buyerRepository, ProductRepository productRepository) {
        this.buyerRepository = buyerRepository;
        this.productRepository = productRepository;
    }

    public List<Product> findProductsOfBuyerWithId(long id) {
        return buyerRepository.findByIdWithProducts(id).getProducts();
    }

    public List<Buyer> findBuyersOfProductWithId(long id) {
        return productRepository.findByIdWithBuyers(id).getBuyers();
    }
}
