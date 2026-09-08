/* 
package com.example.myproject.demo;

import com.example.myproject.demo.entity.Product;
import com.example.myproject.demo.service.ProductService;
import com.example.myproject.demo.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

        private ProductRepository productRepository;
        private ProductService productService;

        @BeforeEach
        void setUp() {

                productRepository = mock(ProductRepository.class);

                productService = new ProductService(productRepository);
        }

        // ============================================================
        // CREATE
        // ============================================================

        @ParameterizedTest
        @CsvFileSource(resources = "/products.csv", numLinesToSkip = 0)
        void shouldCreateProduct(
                        Long id,
                        String name,
                        String price,
                        String description) {

                // Arrange
                Product product = new Product();

                product.setId(id);
                product.setName(name);
                product.setPrice(new BigDecimal(price));
                product.setDescription(description);

                when(productRepository.save(product))
                                .thenReturn(product);

                // Act
                Product result = productService.createProduct(product);

                // Assert
                assertNotNull(result);

                assertEquals(id, result.getId());
                assertEquals(name, result.getName());
                assertEquals(new BigDecimal(price), result.getPrice());
                assertEquals(description, result.getDescription());

                verify(productRepository, times(1))
                                .save(product);
        }

        // ============================================================
        // READ BY ID
        // ============================================================

        @ParameterizedTest
        @CsvFileSource(resources = "/products.csv")
        void shouldGetProductById(
                        Long id,
                        String name,
                        String price,
                        String description) {

                // Arrange
                Product product = new Product();

                product.setId(id);
                product.setName(name);
                product.setPrice(new BigDecimal(price));
                product.setDescription(description);

                when(productRepository.findById(id))
                                .thenReturn(Optional.of(product));

                // Act
                Product result = productService.getProductById(id);

                // Assert
                assertNotNull(result);

                assertEquals(id, result.getId());
                assertEquals(name, result.getName());
                assertEquals(new BigDecimal(price), result.getPrice());
                assertEquals(description, result.getDescription());

                verify(productRepository, times(1))
                                .findById(id);
        }

        // ============================================================
        // READ ALL
        // ============================================================

        @Test
        void shouldGetAllProducts() {

                // Arrange
                Product product1 = new Product();
                product1.setId(1L);
                product1.setName("Ordinateur");
                product1.setPrice(new BigDecimal("999.99"));
                product1.setDescription("Ordinateur portable");

                Product product2 = new Product();
                product2.setId(2L);
                product2.setName("Téléphone");
                product2.setPrice(new BigDecimal("599.99"));
                product2.setDescription("Smartphone Android");

                Product product3 = new Product();
                product3.setId(3L);
                product3.setName("Tablette");
                product3.setPrice(new BigDecimal("399.99"));
                product3.setDescription("Tablette tactile");

                List<Product> products = List.of(
                                product1,
                                product2,
                                product3);

                when(productRepository.findAll())
                                .thenReturn(products);

                // Act
                List<Product> result = productService.getAllProducts();

                // Assert
                assertNotNull(result);
                assertEquals(3, result.size());

                assertEquals(product1, result.get(0));
                assertEquals(product2, result.get(1));
                assertEquals(product3, result.get(2));

                verify(productRepository, times(1))
                                .findAll();
        }

        // ============================================================
        // READ ALL - LISTE VIDE
        // ============================================================

        @Test
        void shouldReturnEmptyListWhenNoProductsExist() {

                // Arrange
                when(productRepository.findAll())
                                .thenReturn(List.of());

                // Act
                List<Product> result = productService.getAllProducts();

                // Assert
                assertNotNull(result);
                assertTrue(result.isEmpty());

                verify(productRepository, times(1))
                                .findAll();
        }

        // ============================================================
        // UPDATE
        // ============================================================

        @ParameterizedTest
        @CsvFileSource(resources = "/products.csv")
        void shouldUpdateProduct(
                        Long id,
                        String name,
                        String price,
                        String description) {

                // Produit existant
                Product existingProduct = new Product();

                existingProduct.setId(id);
                existingProduct.setName("Ancien nom");
                existingProduct.setPrice(new BigDecimal("10.00"));
                existingProduct.setDescription("Ancienne description");

                // Nouvelles informations
                Product productDetails = new Product();

                productDetails.setName(name);
                productDetails.setPrice(new BigDecimal(price));
                productDetails.setDescription(description);

                // Arrange
                when(productRepository.findById(id))
                                .thenReturn(Optional.of(existingProduct));

                when(productRepository.save(existingProduct))
                                .thenReturn(existingProduct);

                // Act
                Product result = productService.updateProduct(
                                id,
                                productDetails);

                // Assert
                assertNotNull(result);

                assertEquals(id, result.getId());
                assertEquals(name, result.getName());
                assertEquals(
                                new BigDecimal(price),
                                result.getPrice());
                assertEquals(
                                description,
                                result.getDescription());
                
                verify(productRepository, times(1))
                                .findById(id);

                verify(productRepository, times(1))
                                .save(existingProduct);
        }

        // ============================================================
        // DELETE
        // ============================================================

        @ParameterizedTest
        @CsvFileSource(resources = "/products.csv")
        void shouldDeleteProduct(
                        Long id,
                        String name,
                        String price,
                        String description) {

                // Arrange
                Product product = new Product();

                product.setId(id);
                product.setName(name);
                product.setPrice(new BigDecimal(price));
                product.setDescription(description);

                when(productRepository.findById(id))
                                .thenReturn(Optional.of(product));

                // Act
                productService.deleteProduct(id);

                // Assert
                verify(productRepository, times(1))
                                .findById(id);

                verify(productRepository, times(1))
                                .delete(product);
        }

        // ============================================================
        // ERROR - GET BY ID
        // ============================================================

        @Test
        void shouldThrowExceptionWhenProductDoesNotExist() {

                // Arrange
                Long id = 999L;

                when(productRepository.findById(id))
                                .thenReturn(Optional.empty());

                // Act & Assert
                RuntimeException exception = assertThrows(
                                RuntimeException.class,
                                () -> productService.getProductById(id));

                assertEquals(
                                "Produit introuvable avec l'id : " + id,
                                exception.getMessage());

                verify(productRepository, times(1))
                                .findById(id);
        }

        // ============================================================
        // ERROR - UPDATE
        // ============================================================

        @Test
        void shouldThrowExceptionWhenUpdatingNonExistingProduct() {

                // Arrange
                Long id = 999L;

                Product productDetails = new Product();

                productDetails.setName("Nouveau produit");
                productDetails.setPrice(new BigDecimal("100.00"));
                productDetails.setDescription("Description");

                when(productRepository.findById(id))
                                .thenReturn(Optional.empty());

                // Act & Assert
                RuntimeException exception = assertThrows(
                                RuntimeException.class,
                                () -> productService.updateProduct(
                                                id,
                                                productDetails));

                assertEquals(
                                "Produit introuvable avec l'id : " + id,
                                exception.getMessage());

                verify(productRepository, times(1))
                                .findById(id);

                // Le produit n'existe pas,
                // donc save() ne doit jamais être appelé.
                verify(productRepository, never())
                                .save(any(Product.class));
        }

        // ============================================================
        // ERROR - DELETE
        // ============================================================

        @Test
        void shouldThrowExceptionWhenDeletingNonExistingProduct() {

                // Arrange
                Long id = 999L;

                when(productRepository.findById(id))
                                .thenReturn(Optional.empty());

                // Act & Assert
                RuntimeException exception = assertThrows(
                                RuntimeException.class,
                                () -> productService.deleteProduct(id));

                assertEquals(
                                "Produit introuvable avec l'id : " + id,
                                exception.getMessage());

                verify(productRepository, times(1))
                                .findById(id);

                // Le produit n'existe pas,
                // donc delete() ne doit jamais être appelé.
                verify(productRepository, never())
                                .delete(any(Product.class));
        }
}
*/