package com.porejemplo.persist;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import java.util.List;

public class ProductRepository {

    private final EntityManagerFactory emFactory;

    public ProductRepository(EntityManagerFactory emFactory) {
        this.emFactory = emFactory;
    }

    public List<Product> findAll() {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();
        List<Product> result = em.createQuery("from Product", Product.class).getResultList();
        em.getTransaction().commit();
        em.close();
        return result;
    }

    public Product findById(long id) {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();
        Product result = em.find(Product.class, id);
        em.getTransaction().commit();
        em.close();
        return result;
    }

    public void insert(Product product) {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(product);
        em.getTransaction().commit();
        em.close();
    }

    public void update(Product product) {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();
        if (product.getId() != null) {
            Product currentProduct = em.find(Product.class, product.getId());
            currentProduct.setTitle(product.getTitle());
            currentProduct.setDescription(product.getDescription());
            currentProduct.setPrice(product.getPrice());
        } else em.persist(product);
        em.getTransaction().commit();
        em.close();
    }

    public void delete(long id) {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();
        Product productToDelete = em.find(Product.class, id);
        if (productToDelete != null) em.remove(productToDelete);
        em.getTransaction().commit();
        em.close();
    }

}
