package com.porejemplo;

import com.porejemplo.persist.Product;
import com.porejemplo.persist.ProductRepository;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManagerFactory;

public class DemoOfProductRepository {
    public static void main(String[] args) {
        EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        ProductRepository productRepository = new ProductRepository(emFactory);

        productRepository.insert(new Product("milk", "3.2%", 50));
        productRepository.insert(new Product("cheese", "Gauda", 200));
        productRepository.insert(new Product("bread", "Borodinsky", 30));
        productRepository.insert(new Product("bread", "Borodinsky", 30));

        System.out.println(productRepository.findAll());

        Product secondProduct = productRepository.findById(2L);
        System.out.println(secondProduct);

        secondProduct.setPrice(300);
        productRepository.update(secondProduct);
        productRepository.update(new Product("fish", "salmon", 500));

        productRepository.delete(1L);

        productRepository.delete(500L);

        System.out.println(productRepository.findAll());

        emFactory.close();
    }
}
