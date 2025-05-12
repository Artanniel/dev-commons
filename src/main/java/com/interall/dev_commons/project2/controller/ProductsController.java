package com.interall.dev_commons.project2.controller;

import com.interall.dev_commons.project1.service.CarService;
import com.interall.dev_commons.project2.model.Product;
import com.interall.dev_commons.project2.repository.ProductsRepository;
import com.interall.dev_commons.project2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public List<Product> getProducts() {
        return productService.listProducts();
    }

    @PostMapping
    public Product createPoduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }
}
