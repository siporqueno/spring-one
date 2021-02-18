package com.porejemplo.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ItemService<T> {

    List<T> findAll();

    List<T> findWithFilterLike(String filterLike);

    List<T> findWithFilterBetween(BigDecimal minFilter, BigDecimal maxFilter);

    List<T> findWithFilterGreaterThanEqual(BigDecimal minFilter);

    List<T> findWithFilterLessThanEqual(BigDecimal maxFilter);

    Optional<T> findById(long id);

    void save(T item);

    void delete(long id);

    public default boolean isBigDecimalInIt(Optional<String> input) {
        if (input.isEmpty()) return false;

        try {
            new BigDecimal(input.get());
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }
}
