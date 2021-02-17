package com.porejemplo.task_four.persist;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ProductRepository {

    private final EntityManagerFactory emFactory;

    public ProductRepository(EntityManagerFactory emFactory) {
        this.emFactory = emFactory;
    }

    public List<Product> findAll() {
        return executeForEntityManager(em -> em.createQuery("from Product", Product.class).getResultList());
    }

    public Product findById(long id) {
        return executeForEntityManager(em -> em.find(Product.class, id));
    }

    public Product findByIdWithBuyerProducts(long id) {
        return executeForEntityManager(em -> em.createNamedQuery("productByIdWithBuyerProducts", Product.class).setParameter("id", id).getSingleResult());
    }

    public void insert(Product product) {
        executeInTransaction(em -> em.persist(product));
    }

    public void update(Product product) {
        executeInTransaction(em -> em.merge(product));
    }

    public void delete(long id) {
        executeInTransaction(em -> {
            Product productToDelete = em.find(Product.class, id);
            if (productToDelete != null) em.remove(productToDelete);
        });
    }

    private <R> R executeForEntityManager(Function<EntityManager, R> function) {
        EntityManager em = emFactory.createEntityManager();
        try {
            return function.apply(em);
        } finally {
            if (em != null) em.close();
        }
    }

    private void executeInTransaction(Consumer<EntityManager> consumer) {
        EntityManager em = emFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            consumer.accept(em);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        } finally {
            if (em != null) em.close();
        }
    }
}
