package com.porejemplo.service;

import com.porejemplo.persist.Product;
import com.porejemplo.persist.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService implements ItemService<ProductRepr> {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductRepr> findAll() {
        return productRepository.findAll().stream()
                .map(ProductRepr::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductRepr> findWithFilterLike(String filter) {
        return productRepository.findProductByTitleLike(filter).stream()
                .map(ProductRepr::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductRepr> findWithFilterBetween(BigDecimal minFilter, BigDecimal maxFilter) {
        return productRepository.findByPriceBetween(minFilter, maxFilter).stream()
                .map(ProductRepr::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductRepr> findWithFilterGreaterThanEqual(BigDecimal minFilter) {
        return productRepository.findByPriceGreaterThanEqual(minFilter).stream()
                .map(ProductRepr::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductRepr> findWithFilterLessThanEqual(BigDecimal maxFilter) {
        return productRepository.findByPriceLessThanEqual(maxFilter).stream()
                .map(ProductRepr::new)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Optional<ProductRepr> findById(long id) {
        return productRepository.findById(id)
                .map(ProductRepr::new);
    }

    @Transactional
    @Override
    public void save(ProductRepr item) {
        productRepository.save(new Product(item));
    }

    @Transactional
    @Override
    public void delete(long id) {
        productRepository.deleteById(id);
    }
}
