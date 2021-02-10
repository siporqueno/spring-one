package com.porejemplo.persist;

import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class UserRepository {

    private final EntityManagerFactory emFactory;

    public UserRepository(EntityManagerFactory emFactory) {
        this.emFactory = emFactory;
    }

    public List<User> findAll() {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();
        List<User> result = em.createNamedQuery("allUsers").getResultList();
        em.getTransaction().commit();
        em.close();
        return result;
    }

    public User findById(long id) {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();
        User result = em.find(User.class, id);
        em.getTransaction().commit();
        em.close();
        return result;
    }

    public void insert(User user) {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();

        try {
            em.persist(user);
        } catch (PersistenceException pe) {
            System.out.printf("Could not insert the user: " + user+ "\n");
            pe.printStackTrace();
        }

        em.getTransaction().commit();
        em.close();
    }

    public void update(User user) {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();
        if (user.getId() != null) {
            User currentUser = em.find(User.class, user.getId());
            currentUser.setEmail(user.getEmail());
            currentUser.setUsername(user.getUsername());
            currentUser.setPassword(user.getPassword());
        } else em.persist(user);
        em.getTransaction().commit();
        em.close();
    }

    public void delete(long id) {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();
        User userToDelete = em.find(User.class, id);
        if (userToDelete != null) em.remove(userToDelete);
        em.getTransaction().commit();
        em.close();
    }

}
