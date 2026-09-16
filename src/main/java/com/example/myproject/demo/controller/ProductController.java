package com.example.myproject.demo.controller;

import com.example.myproject.demo.dto.ProductRequest;
import com.example.myproject.demo.entity.Product;
import com.example.myproject.demo.service.ProductService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// 💡 Imports requis pour le validateur et Jackson
import com.example.myproject.demo.service.JsonRequestValidator;
import com.fasterxml.jackson.databind.ObjectMapper;

// 💡 Imports requis pour la structure des réponses d'erreurs (Map)
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:5003")
public class ProductController {

        // 💡 1. DÉCLARATION DES ATTRIBUTS DE CLASSE (Indispensable pour enlever le
        // rouge)
        private final ProductService productService;
        private final JsonRequestValidator jsonRequestValidator;
        private final ObjectMapper objectMapper;

        // 💡 2. LE CONSTRUCTEUR (Spring injecte automatiquement les dépendances ici)
        public ProductController(ProductService productService, JsonRequestValidator jsonRequestValidator) {
                this.productService = productService;
                this.jsonRequestValidator = jsonRequestValidator;
                this.objectMapper = new ObjectMapper(); // Initialisation de Jackson
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
        // Spring Boot utilise la bibliothèque Jackson pour sérialiser l'objet Java
        // sauvegardé en texte JSON.
        // Par défaut, Jackson applique une règle stricte :
        // toute méthode publique qui commence par get...() et
        // ne prend pas de paramètre est considérée comme un attribut à inclure dans le
        // JSON.
        //
        // @JsonIgnore // 💡 Dit à Jackson de ne PAS inclure cette méthode dans le JSON
        // final
        // public String getType() {
        // return "product";
        // }
        //
        // =========================================================

        @PostMapping
        public ResponseEntity<?> createProduct(@RequestBody String rawJsonBody) {
                ProductRequest request;

                try {
                        // 1. Jackson lit "productType" et instancie la bonne sous-classe (ex:
                        // BookRequest)
                        request = objectMapper.readValue(rawJsonBody, ProductRequest.class);
                } catch (Exception e) {
                        return ResponseEntity.badRequest().body(Map.of(
                                        "status", "BAD_REQUEST",
                                        "message", "Le JSON brut est mal formé ou le type de produit est invalide."));
                }

                // 2. 🚀 ACTION : On lance la validation sur la classe concrète instanciée par
                // Jackson !
                // Si request est un BookRequest, request.getClass() renverra BookRequest.class
                List<String> validationErrors = jsonRequestValidator.validate(rawJsonBody, request.getClass(), true);

                // 3. S'il y a des écarts par rapport au schéma (ex: prix négatif, champ requis
                // manquant)
                if (!validationErrors.isEmpty()) {
                        return ResponseEntity.badRequest().body(Map.of(
                                        "status", "BAD_REQUEST",
                                        "message", "Données non conformes au schéma JSON de création.",
                                        "errors", validationErrors));
                }

                // 4. Persistance en BDD via le service métier
                Product product = productService.createProduct(request);
                return ResponseEntity.status(HttpStatus.CREATED).body(product);
        }

        // =========================================================
        // UPDATE PAR TYPE + ID (PUT -> Validation stricte exigeant l'ID)
        // =========================================================

        @PutMapping("/{type}/{id}")
        public ResponseEntity<?> updateProduct(
                        @PathVariable String type,
                        @PathVariable Long id,
                        @RequestBody String rawJsonBody) {

                ProductRequest request;

                // 1. Jackson tente de lire le polymorphisme et la syntaxe brute
                try {
                        request = objectMapper.readValue(rawJsonBody, ProductRequest.class);
                } catch (Exception e) {
                        return ResponseEntity.badRequest().body(Map.of(
                                        "status", "BAD_REQUEST",
                                        "message",
                                        "Le JSON brut est mal formé ou les attributs polymorphes sont invalides."));
                }

                // 2. 🚀 ACTION : Validation stricte via le schéma de MODIFICATION (isCreation =
                // false)
                // Ce schéma va lever une erreur si le champ "id" est absent du JSON.
                List<String> validationErrors = jsonRequestValidator.validate(rawJsonBody, request.getClass(), false);

                if (!validationErrors.isEmpty()) {
                        return ResponseEntity.badRequest().body(Map.of(
                                        "status", "BAD_REQUEST",
                                        "message",
                                        "Données non conformes au schéma JSON de modification (L'attribut 'id' est notamment requis).",
                                        "errors", validationErrors));
                }

                // 3. Sécurité applicative : On s'assure que l'ID du JSON correspond bien à l'ID
                // de l'URL
                if (!id.equals(request.getId())) {
                        return ResponseEntity.badRequest().body(Map.of(
                                        "status", "BAD_REQUEST",
                                        "message",
                                        "Incohérence détectée : L'ID de l'URL (" + id
                                                        + ") ne correspond pas à l'ID du corps JSON (" + request.getId()
                                                        + ")."));
                }

                // 4. Tout est validé, transmission de la mise à jour au service métier
                try {
                        Product updatedProduct = productService.updateProduct(id, request);
                        return ResponseEntity.ok(updatedProduct);
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(Map.of("error", "Erreur lors de la modification : " + e.getMessage()));
                }
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