package com.porejemplo.task_four.persist;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class BuyerProductRepository {

    private final EntityManagerFactory emFactory;

    public BuyerProductRepository(EntityManagerFactory emFactory) {
        this.emFactory = emFactory;
    }

    public List<BuyerProduct> findAll() {
        return executeForEntityManager(em -> em.createNamedQuery("allBuyerProducts").getResultList());
    }

    public BuyerProduct findById(long id) {
        return executeForEntityManager(em -> em.find(BuyerProduct.class, id));
    }

    public void insert(BuyerProduct buyerProduct) {
        executeInTransaction(em -> em.persist(buyerProduct));
    }

    public void update(BuyerProduct buyerProduct) {
        executeInTransaction(em -> em.merge(buyerProduct));
    }

    public void delete(long id) {
        executeInTransaction(em -> {
            BuyerProduct buyerProductToDelete = em.find(BuyerProduct.class, id);
            if (buyerProductToDelete != null) em.remove(buyerProductToDelete);
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
