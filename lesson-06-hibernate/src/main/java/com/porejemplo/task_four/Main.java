package com.porejemplo.task_four;

import com.porejemplo.task_four.persist.*;
import com.porejemplo.task_four.service.InfoService;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate_task_four.cfg.xml")
                .buildSessionFactory();

        BuyerRepository buyerRepository = new BuyerRepository(emFactory);
        ProductRepository productRepository = new ProductRepository(emFactory);
        BuyerProductRepository buyerProductRepository = new BuyerProductRepository(emFactory);
        InfoService infoService = new InfoService(buyerRepository, productRepository);

        // Population of tables
        Buyer buyerIvan = new Buyer("Ivan");
        Buyer buyerPetr = new Buyer("Petr");
        buyerRepository.insert(buyerIvan);
        buyerRepository.insert(buyerPetr);

        Product productMilk = new Product("milk", 50);
        Product productBread = new Product("bread", 20);
        Product productCheese = new Product("cheese", 100);
        productRepository.insert(productMilk);
        productRepository.insert(productBread);
        productRepository.insert(productCheese);

        BuyerProduct ivanBoughtMilk = new BuyerProduct(buyerIvan, productMilk);
        BuyerProduct ivanBoughtBread = new BuyerProduct(buyerIvan, productBread);
        BuyerProduct petrBoughtBread = new BuyerProduct(buyerPetr, productBread);
        BuyerProduct petrBoughtCheese = new BuyerProduct(buyerPetr, productCheese);
        buyerProductRepository.insert(ivanBoughtMilk);
        buyerProductRepository.insert(ivanBoughtBread);
        buyerProductRepository.insert(petrBoughtBread);
        buyerProductRepository.insert(petrBoughtCheese);

        // This way update works
        productMilk=productRepository.findById(1L);
        productMilk.setPrice(40);
        productRepository.update(productMilk);

        // This way it doesn't work
        productBread.setId(2L);
        productBread.setPrice(25);
        productRepository.update(productBread);

        // Test of InfoService
        System.out.println("Ivan bought: " + infoService.findProductsOfBuyerWithId(1L));
        System.out.println("Buyers of bread: " + infoService.findBuyersOfProductWithId(2L));

        // Test of price paid statistic
        System.out.println("Ivan bought: "+listOfProductsWithPricePaid(infoService.findBuyerProductsOfBuyerWithId(1L)));

        emFactory.close();
    }

    private static String listOfProductsWithPricePaid(List<BuyerProduct> buyerProducts) {
        return buyerProducts.stream().map(bp -> bp.getProduct().toString() + " pricePaid: " + bp.getPricePaid()).collect(Collectors.joining("\n"));
    }
}
