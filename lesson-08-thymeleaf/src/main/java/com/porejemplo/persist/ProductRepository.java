package com.porejemplo.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


//        "milk","3.2%",  50
//        "cheese","Gauda", 200
//        "meat", "pork",500

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductByTitleLike(String title);

    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    List<Product> findByPriceGreaterThanEqual(BigDecimal minPrice);

    List<Product> findByPriceLessThanEqual(BigDecimal maxPrice);

}
