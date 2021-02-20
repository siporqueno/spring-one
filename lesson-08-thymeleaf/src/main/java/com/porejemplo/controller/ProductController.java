package com.porejemplo.controller;

import com.porejemplo.persist.Product;
import com.porejemplo.service.ItemService;
import com.porejemplo.service.ProductRepr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private ItemService<ProductRepr> productService;

    @Autowired
    public ProductController(ItemService<ProductRepr> productService) {
        this.productService = productService;
    }

    @GetMapping
    public String listPage(Model model,
                           @RequestParam("minPrice") Optional<String> minPrice,
                           @RequestParam("maxPrice") Optional<String> maxPrice) {
        logger.info("List page requested");

        List<ProductRepr> products;
        if (minPrice.isPresent() && productService.isBigDecimalInIt(minPrice)) {
            if (maxPrice.isPresent() && productService.isBigDecimalInIt(maxPrice)) {
                products = productService.findWithFilterBetween(new BigDecimal(minPrice.get()), new BigDecimal(maxPrice.get()));
            } else {
                products = productService.findWithFilterGreaterThanEqual(new BigDecimal(minPrice.get()));
            }
        } else if (maxPrice.isPresent() && productService.isBigDecimalInIt(maxPrice)) {
            products = productService.findWithFilterLessThanEqual(new BigDecimal(maxPrice.get()));
        } else {
            products = productService.findAll();
        }

        model.addAttribute("products", products);
        return "product";
    }

    @GetMapping("/{id}")
    public String editPage(@PathVariable("id") Long id, Model model) {
        logger.info("Edit page for id {} requested", id);

        model.addAttribute("product", productService.findById(id)
                .orElseThrow(NotFoundException::new));
        return "product_form";
    }

    @PostMapping("/update")
    public String update(@Valid ProductRepr productRepr, BindingResult result) {
        logger.info("Update endpoint requested");

        if (result.hasErrors()) {
            return "product_form";
        }

        logger.info("Updating product with id {}", productRepr.getId());
        productService.save(productRepr);
        return "redirect:/product";
    }

    @GetMapping("/new")
    public String create(Model model) {
        logger.info("New endpoint requested");
        model.addAttribute("product", new Product());
        return "product_form";
    }

    @DeleteMapping("/{id}")
    public String remove(@PathVariable("id") Long id) {
        logger.info("Delete endpoint requested for product with id {}", id);
        productService.delete(id);
        return "redirect:/product";
    }
}
