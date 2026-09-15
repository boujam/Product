package com.example.myproject.demo.controller;

import com.example.myproject.demo.dto.ProductRequest;
import com.example.myproject.demo.entity.Product;
import com.example.myproject.demo.service.ProductService;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
                                id);

        }

        // =========================================================
        // CREATE
        // Lorsque votre contrôleur effectue un return ResponseEntity.ok(product);, 
        // Spring Boot utilise la bibliothèque Jackson pour sérialiser l'objet Java sauvegardé en texte JSON.
        // Par défaut, Jackson applique une règle stricte : 
        // toute méthode publique qui commence par get...() et 
        // ne prend pas de paramètre est considérée comme un attribut à inclure dans le JSON.
        //
        //    @JsonIgnore // 💡 Dit à Jackson de ne PAS inclure cette méthode dans le JSON final
        //    public String getType() {
        //        return "product";
        //    }
        //
        // =========================================================

        @PostMapping
        public ResponseEntity<Product> createProduct(
                        @RequestBody ProductRequest request) {

                System.out.println("type de produit créé: " + request.getProductType());

                System.out.println("json reçu : " + request.toString());                

                Product product = productService.createProduct(request);

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
                                id,
                                request);

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
                                id);

                return ResponseEntity
                                .noContent()
                                .build();

        }

}