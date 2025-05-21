package com.artantech.dev_commons.project2.service;

import com.artantech.dev_commons.project2.entity.Product;
import com.artantech.dev_commons.project2.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductsRepository productRepository;

    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}
