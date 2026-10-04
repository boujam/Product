package com.example.myproject.demo;

import com.example.myproject.demo.entity.Product;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ProductTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    /**
     * Implémentation concrète de Product uniquement pour les tests.
     * Product étant abstract, il faut une classe concrète pour pouvoir
     * l'instancier.
     */
    private static class TestProduct extends Product {
        // Aucun champ supplémentaire nécessaire.
    }

    @BeforeAll
    static void setUpValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void tearDownValidator() {
        validatorFactory.close();
    }

    // ============================================================
    // getProductType()
    // ============================================================

    @Test
    void shouldReturnProductAsProductType() {
        Product product = new TestProduct();

        assertEquals("product", product.getProductType());
    }

    @Test
    void shouldSetProductTypeToProductWhenGetProductTypeIsCalled() {
        Product product = new TestProduct();

        product.setProductType("something-else");

        assertEquals("product", product.getProductType());
        assertEquals("product", product.getProductType());
    }

    // ============================================================
    // Validation - name
    // ============================================================

    @Test
    void shouldAcceptValidName() {
        Product product = validProduct();
        product.setName("Mon produit");

        assertNoViolations(product);
    }

    @Test
    void shouldRejectNullName() {
        Product product = validProduct();
        product.setName(null);

        assertViolationOnProperty(product, "name");
    }

    @Test
    void shouldRejectEmptyName() {
        Product product = validProduct();
        product.setName("");

        assertViolationOnProperty(product, "name");
    }

    @Test
    void shouldRejectBlankName() {
        Product product = validProduct();
        product.setName("   ");

        assertViolationOnProperty(product, "name");
    }

    @Test
    void shouldAcceptNameWithExactly100Characters() {
        Product product = validProduct();
        product.setName("a".repeat(100));

        assertNoViolationOnProperty(product, "name");
    }

    @Test
    void shouldRejectNameWithMoreThan100Characters() {
        Product product = validProduct();
        product.setName("a".repeat(101));

        assertViolationOnProperty(product, "name");
    }

    // ============================================================
    // Validation - price
    // ============================================================

    @Test
    void shouldAcceptPositivePrice() {
        Product product = validProduct();
        product.setPrice(new BigDecimal("12.99"));

        assertNoViolationOnProperty(product, "price");
    }

    @Test
    void shouldAcceptZeroPrice() {
        Product product = validProduct();
        product.setPrice(BigDecimal.ZERO);

        assertNoViolationOnProperty(product, "price");
    }

    @Test
    void shouldRejectNullPrice() {
        Product product = validProduct();
        product.setPrice(null);

        assertViolationOnProperty(product, "price");
    }

    @Test
    void shouldRejectNegativePrice() {
        Product product = validProduct();
        product.setPrice(new BigDecimal("-0.01"));

        assertViolationOnProperty(product, "price");
    }

    @Test
    void shouldAcceptPriceWithLargePositiveValue() {
        Product product = validProduct();
        product.setPrice(new BigDecimal("9999999.99"));

        assertNoViolationOnProperty(product, "price");
    }

    // ============================================================
    // Validation - description
    // ============================================================

    @Test
    void shouldAcceptNullDescription() {
        Product product = validProduct();
        product.setDescription(null);

        assertNoViolationOnProperty(product, "description");
    }

    @Test
    void shouldAcceptEmptyDescription() {
        Product product = validProduct();
        product.setDescription("");

        assertNoViolationOnProperty(product, "description");
    }

    @Test
    void shouldAcceptDescriptionWithExactly1000Characters() {
        Product product = validProduct();
        product.setDescription("a".repeat(1000));

        assertNoViolationOnProperty(product, "description");
    }

    @Test
    void shouldRejectDescriptionWithMoreThan1000Characters() {
        Product product = validProduct();
        product.setDescription("a".repeat(1001));

        assertViolationOnProperty(product, "description");
    }

    // ============================================================
    // equals()
    // ============================================================

    @Test
    void shouldBeEqualToItself() {
        Product product = validProduct();

        assertEquals(product, product);
    }

    @Test
    void shouldNotBeEqualToNull() {
        Product product = validProduct();

        assertNotEquals(product, null);
    }

    @Test
    void shouldNotBeEqualToObjectOfAnotherClass() {
        Product product = validProduct();

        assertNotEquals(product, new Object());
    }

    @Test
    void shouldBeEqualWhenAllFieldsAreEqual() {
        Product product1 = validProduct();
        Product product2 = validProduct();

        assertEquals(product1, product2);
    }

    @Test
    void shouldNotBeEqualWhenIdsAreDifferent() {
        Product product1 = validProduct();
        Product product2 = validProduct();

        product1.setId(1L);
        product2.setId(2L);

        assertNotEquals(product1, product2);
    }

    @Test
    void shouldBeEqualWhenIdsAreTheSame() {
        Product product1 = validProduct();
        Product product2 = validProduct();

        product1.setId(1L);
        product2.setId(1L);

        assertEquals(product1, product2);
    }

    @Test
    void shouldNotBeEqualWhenNamesAreDifferent() {
        Product product1 = validProduct();
        Product product2 = validProduct();

        product1.setName("Produit A");
        product2.setName("Produit B");

        assertNotEquals(product1, product2);
    }

    @Test
    void shouldNotBeEqualWhenPricesAreDifferent() {
        Product product1 = validProduct();
        Product product2 = validProduct();

        product1.setPrice(new BigDecimal("10.00"));
        product2.setPrice(new BigDecimal("20.00"));

        assertNotEquals(product1, product2);
    }

    @Test
    void shouldNotBeEqualWhenDescriptionsAreDifferent() {
        Product product1 = validProduct();
        Product product2 = validProduct();

        product1.setDescription("Description A");
        product2.setDescription("Description B");

        assertNotEquals(product1, product2);
    }

    @Test
    void shouldHandleNullFieldsInEquals() {
        Product product1 = new TestProduct();
        Product product2 = new TestProduct();

        assertEquals(product1, product2);
    }

    @Test
    void shouldNotBeEqualWhenOneIdIsNullAndTheOtherIsNot() {
        Product product1 = validProduct();
        Product product2 = validProduct();

        product1.setId(null);
        product2.setId(1L);

        assertNotEquals(product1, product2);
    }

    @Test
    void shouldNotBeEqualWhenOneNameIsNullAndTheOtherIsNot() {
        Product product1 = validProduct();
        Product product2 = validProduct();

        product1.setName(null);
        product2.setName("Produit");

        assertNotEquals(product1, product2);
    }

    @Test
    void shouldNotBeEqualWhenOnePriceIsNullAndTheOtherIsNot() {
        Product product1 = validProduct();
        Product product2 = validProduct();

        product1.setPrice(null);
        product2.setPrice(BigDecimal.TEN);

        assertNotEquals(product1, product2);
    }

    @Test
    void shouldNotBeEqualWhenOneDescriptionIsNullAndTheOtherIsNot() {
        Product product1 = validProduct();
        Product product2 = validProduct();

        product1.setDescription(null);
        product2.setDescription("Description");

        assertNotEquals(product1, product2);
    }

    // ============================================================
    // hashCode()
    // ============================================================

    @Test
    void shouldHaveSameHashCodeForEqualObjects() {
        Product product1 = validProduct();
        Product product2 = validProduct();

        assertEquals(product1, product2);
        assertEquals(product1.hashCode(), product2.hashCode());
    }

    @Test
    void shouldProduceDifferentHashCodeWhenIdChanges() {
        Product product1 = validProduct();
        Product product2 = validProduct();

        product1.setId(1L);
        product2.setId(2L);

        assertNotEquals(product1.hashCode(), product2.hashCode());
    }

    @Test
    void shouldProduceDifferentHashCodeWhenNameChanges() {
        Product product1 = validProduct();
        Product product2 = validProduct();

        product1.setName("Produit A");
        product2.setName("Produit B");

        assertNotEquals(product1.hashCode(), product2.hashCode());
    }

    @Test
    void shouldProduceDifferentHashCodeWhenPriceChanges() {
        Product product1 = validProduct();
        Product product2 = validProduct();

        product1.setPrice(new BigDecimal("10.00"));
        product2.setPrice(new BigDecimal("20.00"));

        assertNotEquals(product1.hashCode(), product2.hashCode());
    }

    @Test
    void shouldProduceDifferentHashCodeWhenDescriptionChanges() {
        Product product1 = validProduct();
        Product product2 = validProduct();

        product1.setDescription("Description A");
        product2.setDescription("Description B");

        assertNotEquals(product1.hashCode(), product2.hashCode());
    }

    // ============================================================
    // Helpers
    // ============================================================

    private static Product validProduct() {
        Product product = new TestProduct();

        product.setName("Produit test");
        product.setPrice(new BigDecimal("10.00"));
        product.setDescription("Description test");

        return product;
    }

    private static void assertNoViolations(Product product) {
        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertTrue(
                violations.isEmpty(),
                () -> "Violations inattendues : " + violations);
    }

    private static void assertNoViolationOnProperty(
            Product product,
            String property) {
        Set<ConstraintViolation<Product>> violations = validator.validateProperty(product, property);

        assertTrue(
                violations.isEmpty(),
                () -> "Violation inattendue sur '" + property
                        + "' : " + violations);
    }

    private static void assertViolationOnProperty(
            Product product,
            String property) {
        Set<ConstraintViolation<Product>> violations = validator.validateProperty(product, property);

        assertFalse(
                violations.isEmpty(),
                () -> "Aucune violation détectée sur '" + property + "'");
    }
}
