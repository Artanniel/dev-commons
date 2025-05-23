package com.artantech.dev_commons.project2.repository;

import com.artantech.dev_commons.project2.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Product, Long> {
}
