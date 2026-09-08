package com.example.myproject.demo.controller;

import com.example.myproject.demo.entity.Product;
import com.example.myproject.demo.service.ProductService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:5003")
public class ProductController {

private final ProductService productService;

public ProductController(ProductService productService) {
    this.productService = productService;
}


// =========================================================
// GET ALL
// =========================================================

@GetMapping
public List<Product> getAllProducts() {

    return productService.getAllProducts();

}


// =========================================================
// GET ALL PAR TYPE
// =========================================================

@GetMapping("/{type}")
public List<Product> getProductsByType(
        @PathVariable String type) {

    return productService.getProductsByType(type);

}


// =========================================================
// GET ONE PAR TYPE + ID
// =========================================================

@GetMapping("/{type}/{id}")
public Product getProductByTypeAndId(
        @PathVariable String type,
        @PathVariable Long id) {

    return productService.getProductByTypeAndId(
            type,
            id
    );

}


// =========================================================
// CREATE
// =========================================================

@PostMapping
public ResponseEntity<Product> createProduct(
        @RequestBody ProductRequest request) {

    Product product =
            productService.createProduct(request);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(product);

}


// =========================================================
// UPDATE PAR TYPE + ID
// =========================================================

@PutMapping("/{type}/{id}")
public Product updateProduct(
        @PathVariable String type,
        @PathVariable Long id,
        @RequestBody ProductRequest request) {

    return productService.updateProduct(
            type,
            id,
            request
    );

}


// =========================================================
// DELETE PAR TYPE + ID
// =========================================================

@DeleteMapping("/{type}/{id}")
public ResponseEntity<Void> deleteProduct(
        @PathVariable String type,
        @PathVariable Long id) {

    productService.deleteProduct(
            type,
            id
    );

    return ResponseEntity
            .noContent()
            .build();

}

}