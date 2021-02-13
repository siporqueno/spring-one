package com.porejemplo;

import com.porejemplo.persist.Buyer;
import com.porejemplo.persist.BuyerRepository;
import com.porejemplo.persist.Product;
import com.porejemplo.persist.ProductRepository;
import com.porejemplo.service.InfoService;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        BuyerRepository buyerRepository = new BuyerRepository(emFactory);
        ProductRepository productRepository = new ProductRepository(emFactory);
        InfoService infoService = new InfoService(buyerRepository, productRepository);

        // Population of databases
        Buyer buyerOne = new Buyer("Ivan");
        Buyer buyerTwo = new Buyer("Petr");
        buyerRepository.insert(buyerOne);
        buyerRepository.insert(buyerTwo);
        List<Buyer> milkBuyersList = new ArrayList<>();
        List<Buyer> breadBuyersList = new ArrayList<>();
        List<Buyer> cheeseBuyersList = new ArrayList<>();
        milkBuyersList.add(buyerOne);
        breadBuyersList.add(buyerOne);
        breadBuyersList.add(buyerTwo);
        cheeseBuyersList.add(buyerTwo);
        productRepository.insert(new Product("milk", 50, milkBuyersList));
        productRepository.insert(new Product("bread", 20, breadBuyersList));
        productRepository.insert(new Product("cheese", 100, cheeseBuyersList));

        // Tests of what we have inserted via repository methods
        System.out.println("Ivan bought: " + buyerRepository.findByIdWithProducts(1L).getProducts());
        System.out.println("Petr bought: " + buyerRepository.findByIdWithProducts(2L).getProducts());
        System.out.println("Milk buyers: " + productRepository.findByIdWithBuyers(1L).getBuyers());
        System.out.println("Bread buyers: " + productRepository.findByIdWithBuyers(2L).getBuyers());
        System.out.println("Cheese buyers: " + productRepository.findByIdWithBuyers(3L).getBuyers());

        // Test of InfoService created as the Task 3 of the Homework
        System.out.println("Ivan bought: " + infoService.findProductsOfBuyerWithId(1L));
        System.out.println("Buyers of bread: " + infoService.findBuyersOfProductWithId(2L));

        emFactory.close();
    }
}
