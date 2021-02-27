package com.porejemplo.persist;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CartRepository {
    private Map<Long, Cart> cartMap = new ConcurrentHashMap<>();

    public List<Cart> findAll() {
        return new ArrayList<>(cartMap.values());
    }

    public Cart findById(long id) {
        return cartMap.get(id);
    }

    public void save(Cart cart) {
        cartMap.put(cart.getId(), cart);
    }

    public void delete(long id) {
        cartMap.remove(id);
    }
}
