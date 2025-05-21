package com.artantech.dev_commons.project2.controller;

import com.artantech.dev_commons.project2.entity.Product;
import com.artantech.dev_commons.project2.service.ProductService;
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
