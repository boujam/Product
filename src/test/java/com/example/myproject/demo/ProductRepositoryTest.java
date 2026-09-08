/*
package com.example.myproject.demo;

import com.example.myproject.demo.entity.Product;
import com.example.myproject.demo.repository.ProductRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;


    // ============================================================
    // CREATE
    // ============================================================

    @Test
    void shouldSaveProduct() {

        // Arrange
        Product product = new Product();

        product.setName("Ordinateur");
        product.setPrice(new BigDecimal("999.99"));
        product.setDescription("Ordinateur portable");

        // Act
        Product savedProduct = productRepository.save(product);

        // Assert
        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getId());

        assertEquals(
                "Ordinateur",
                savedProduct.getName()
        );

        assertEquals(
                new BigDecimal("999.99"),
                savedProduct.getPrice()
        );

        assertEquals(
                "Ordinateur portable",
                savedProduct.getDescription()
        );

    }


    // ============================================================
    // READ BY ID
    // ============================================================

    @Test
    void shouldFindProductById() {

        // Arrange
        Product product = new Product();

        product.setName("Téléphone");
        product.setPrice(new BigDecimal("599.99"));
        product.setDescription("Smartphone Android");

        Product savedProduct = productRepository.save(product);

        // Act
        Optional<Product> result =
                productRepository.findById(savedProduct.getId());

        // Assert
        assertTrue(result.isPresent());

        Product foundProduct = result.get();

        assertEquals(
                savedProduct.getId(),
                foundProduct.getId()
        );

        assertEquals(
                "Téléphone",
                foundProduct.getName()
        );

        assertEquals(
                new BigDecimal("599.99"),
                foundProduct.getPrice()
        );

        assertEquals(
                "Smartphone Android",
                foundProduct.getDescription()
        );

    }


    // ============================================================
    // READ ALL
    // ============================================================

    @Test
    void shouldFindAllProducts() {

        // Arrange
        Product product1 = new Product();

        product1.setName("Ordinateur");
        product1.setPrice(new BigDecimal("999.99"));
        product1.setDescription("Ordinateur portable");

        Product product2 = new Product();

        product2.setName("Téléphone");
        product2.setPrice(new BigDecimal("599.99"));
        product2.setDescription("Smartphone Android");

        productRepository.save(product1);
        productRepository.save(product2);

        // Act
        List<Product> products =
                productRepository.findAll();

        // Assert
        assertNotNull(products);
        assertEquals(2, products.size());

        assertTrue(
                products.stream()
                        .anyMatch(p ->
                                p.getName().equals("Ordinateur")
                        )
        );

        assertTrue(
                products.stream()
                        .anyMatch(p ->
                                p.getName().equals("Téléphone")
                        )
        );
    }


    // ============================================================
    // UPDATE
    // ============================================================

    @Test
    void shouldUpdateProduct() {

        // Arrange
        Product product = new Product();

        product.setName("Ordinateur");
        product.setPrice(new BigDecimal("999.99"));
        product.setDescription("Ordinateur portable");

        Product savedProduct =
                productRepository.save(product);

        // Act
        savedProduct.setName("Nouvel ordinateur");
        savedProduct.setPrice(new BigDecimal("1299.99"));

        Product updatedProduct =
                productRepository.save(savedProduct);

        // Assert
        assertEquals(
                "Nouvel ordinateur",
                updatedProduct.getName()
        );

        assertEquals(
                new BigDecimal("1299.99"),
                updatedProduct.getPrice()
        );

        assertEquals(
                savedProduct.getId(),
                updatedProduct.getId()
        );
    }


    // ============================================================
    // DELETE
    // ============================================================

    @Test
    void shouldDeleteProduct() {

        // Arrange
        Product product = new Product();

        product.setName("Souris");
        product.setPrice(new BigDecimal("49.99"));
        product.setDescription("Souris sans fil");

        Product savedProduct =
                productRepository.save(product);

        Long id = savedProduct.getId();

        // Act
        productRepository.deleteById(id);

        // Assert
        Optional<Product> result =
                productRepository.findById(id);

        assertFalse(result.isPresent());
    }


    // ============================================================
    // READ BY ID - PRODUCT NOT FOUND
    // ============================================================

    @Test
    void shouldReturnEmptyWhenProductDoesNotExist() {

        // Act
        Optional<Product> result =
                productRepository.findById(999999L);

        // Assert
        assertTrue(result.isEmpty());
    }


    // ============================================================
    // READ ALL - EMPTY DATABASE
    // ============================================================

    @Test
    void shouldReturnEmptyListWhenNoProductsExist() {

        // Act
        List<Product> products =
                productRepository.findAll();

        // Assert
        assertNotNull(products);
        assertTrue(products.isEmpty());
    }
}
*/