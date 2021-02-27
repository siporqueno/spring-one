package com.porejemplo.service;

import java.util.List;

public interface CartService {
    List<ProductRepr> findAllProductsOfUserWithId(long userId);

    void addProductByIdToUserWithId(long productId, long userId);

    void deleteProductByIdFromUserWithId(long productId, long userId);

    void deleteAllProductsFromUserWithId(long userId);
}
