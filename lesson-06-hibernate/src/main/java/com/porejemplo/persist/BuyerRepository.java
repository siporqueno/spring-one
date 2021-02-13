package com.porejemplo.persist;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class BuyerRepository {

    private final EntityManagerFactory emFactory;

    public BuyerRepository(EntityManagerFactory emFactory) {
        this.emFactory = emFactory;
    }

    public List<Buyer> findAll() {
        return executeForEntityManager(em -> em.createNamedQuery("allBuyers").getResultList());
    }

    public List<Buyer> findAllWithProducts() {
        return executeForEntityManager(em -> em.createNamedQuery("allBuyersWithProducts").getResultList());
    }

    public Buyer findById(long id) {
        return executeForEntityManager(em -> em.find(Buyer.class, id));
    }

    public Buyer findByIdWithProducts(long id) {
        return executeForEntityManager(em -> em.createNamedQuery("buyerByIdWithProducts", Buyer.class).setParameter("id", id).getSingleResult());
    }

    public void insert(Buyer buyer) {
        executeInTransaction(em -> em.persist(buyer));
    }

    public void update(Buyer buyer) {
        executeInTransaction(em -> em.merge(buyer));
    }

    public void delete(long id) {
        executeInTransaction(em -> {
            Buyer buyerToDelete = em.find(Buyer.class, id);
            if (buyerToDelete != null) em.remove(buyerToDelete);
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
