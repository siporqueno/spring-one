package com.porejemplo.service;

import com.porejemplo.persist.Product;
import com.porejemplo.persist.ProductRepository;
import com.porejemplo.persist.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
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
    public Page<ProductRepr> findWithFilter(String likeTitle, BigDecimal minPrice, BigDecimal maxPrice, Integer page, Integer size) {
        Specification<Product> spec = Specification.where(null);

        if (likeTitle != null && !likeTitle.isBlank()) {
            spec = spec.and(ProductSpecification.titleLike(likeTitle));
        }

        if (minPrice != null) {
            spec = spec.and(ProductSpecification.minPrice(minPrice));
        }

        if (maxPrice != null) {
            spec = spec.and(ProductSpecification.maxPrice(maxPrice));
        }

        return productRepository.findAll(spec, PageRequest.of(page, size))
                .map(ProductRepr::new);
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
