/* 
package com.example.myproject.demo;

import com.example.myproject.demo.entity.Product;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void shouldCreateProduct() {

        // Arrange
        Product product = new Product();

        // Act
        product.setId(1L);
        product.setName("Ordinateur");
        product.setPrice(new BigDecimal("999.99"));
        product.setDescription("Ordinateur portable");

        // Assert
        assertEquals(1L, product.getId());
        assertEquals("Ordinateur", product.getName());
        assertEquals(
                new BigDecimal("999.99"),
                product.getPrice());
        assertEquals(
                "Ordinateur portable",
                product.getDescription());
    }

    @Test
    void shouldGenerateProductToString() {

        // Arrange
        Product product = new Product();

        product.setId(1L);
        product.setName("Ordinateur");
        product.setPrice(new BigDecimal("999.99"));
        product.setDescription("Ordinateur portable");

        // Act
        String result = product.toString();

        // Assert
        assertNotNull(result);

        assertTrue(result.contains("1"));
        assertTrue(result.contains("Ordinateur"));
        assertTrue(result.contains("999.99"));
        assertTrue(result.contains("Ordinateur portable"));
    }

}
*/