package com.interall.dev_commons.repository.schema2;

import com.interall.dev_commons.project2.model.Marketplace;
import com.interall.dev_commons.project2.model.Product;
import com.interall.dev_commons.project2.repository.MarketplaceRepository;
import com.interall.dev_commons.project2.repository.ProductsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional("schema2TransactionManager")
public class MarketplaceProductsRepositoryTest {

    @Autowired
    private MarketplaceRepository marketplaceRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Test
    void testCreateAndFindMarketplace() {
        Marketplace marketplace = new Marketplace();
        marketplace.setName("Loja Virtual");
        marketplace.setDescription("Uma nova loja online");
        marketplace.setCreatedAt(LocalDateTime.now());

        Marketplace savedMarketplace = marketplaceRepository.save(marketplace);

        assertNotNull(savedMarketplace.getId());
        assertEquals("Loja Virtual", savedMarketplace.getName());
        assertEquals("Uma nova loja online", savedMarketplace.getDescription());

        Optional<Marketplace> foundMarketplace = marketplaceRepository.findById(savedMarketplace.getId());
        assertTrue(foundMarketplace.isPresent());
        assertEquals(savedMarketplace.getName(), foundMarketplace.get().getName());
    }

    @Test
    void testCreateAndFindProduct() {
        Marketplace marketplace = new Marketplace();
        marketplace.setName("E-commerce XYZ");
        marketplace.setDescription("Loja de eletrônicos");
        marketplace.setCreatedAt(LocalDateTime.now());
        Marketplace savedMarketplace = marketplaceRepository.save(marketplace);

        Product product = new Product();
        product.setName("Smart TV 4K");
        product.setPrice(2500.00);
        product.setMarketplace(savedMarketplace);

        Product savedProduct = productsRepository.save(product);

        assertNotNull(savedProduct.getId());
        assertEquals("Smart TV 4K", savedProduct.getName());
        assertEquals(2500.00, savedProduct.getPrice());
        assertEquals(savedMarketplace.getId(), savedProduct.getMarketplace().getId());

        Optional<Product> foundProduct = productsRepository.findById(savedProduct.getId());
        assertTrue(foundProduct.isPresent());
        assertEquals(savedProduct.getName(), foundProduct.get().getName());
    }

    @Test
    void testUpdateProduct() {
        Marketplace marketplace = new Marketplace();
        marketplace.setName("Loja ABC");
        marketplace.setDescription("Loja de variedades");
        marketplace.setCreatedAt(LocalDateTime.now());
        Marketplace savedMarketplace = marketplaceRepository.save(marketplace);

        Product product = new Product();
        product.setName("Tablet");
        product.setPrice(800.00);
        product.setMarketplace(savedMarketplace);
        Product savedProduct = productsRepository.save(product);

        savedProduct.setName("Tablet Pro");
        savedProduct.setPrice(1000.00);
        Product updatedProduct = productsRepository.save(savedProduct);

        assertEquals("Tablet Pro", updatedProduct.getName());
        assertEquals(1000.00, updatedProduct.getPrice());

        Optional<Product> foundProduct = productsRepository.findById(updatedProduct.getId());
        assertTrue(foundProduct.isPresent());
        assertEquals("Tablet Pro", foundProduct.get().getName());
    }

    @Test
    void testDeleteMarketplace() {
        Marketplace marketplace = new Marketplace();
        marketplace.setName("Loja Temporária");
        marketplace.setDescription("Loja de teste");
        marketplace.setCreatedAt(LocalDateTime.now());
        Marketplace savedMarketplace = marketplaceRepository.save(marketplace);

        marketplaceRepository.deleteById(savedMarketplace.getId());

        Optional<Marketplace> foundMarketplace = marketplaceRepository.findById(savedMarketplace.getId());
        assertFalse(foundMarketplace.isPresent());
    }

    @Test
    void testFindAllMarketplaces() {
        List<Marketplace> marketplaces = marketplaceRepository.findAll();
        assertTrue(marketplaces.size() >= 3); // Dados iniciais do Flyway
    }
}