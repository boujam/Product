package com.example.myproject.demo.service;

import com.example.myproject.demo.controller.ProductRequest;
import com.example.myproject.demo.entity.Book;
import com.example.myproject.demo.entity.Dvd;
import com.example.myproject.demo.entity.Product;
import com.example.myproject.demo.entity.VideoGame;
import com.example.myproject.demo.repository.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/* 
* LOGIQUE DU SERVICE 
* 1. Vérifier que "dvd" est un type valide
* 2. Chercher le produit avec l'ID 5
* 3. Vérifier son vrai type
* 4. Retourner le produit uniquement s'il est réellement un DVD
*/

@Service
public class ProductService {

private final ProductRepository productRepository;


public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
}


// =========================================================
// GET ALL
// =========================================================

public List<Product> getAllProducts() {

    return productRepository.findAll();

}


// =========================================================
// GET ALL PAR TYPE
// =========================================================

public List<Product> getProductsByType(String type) {

    validateProductType(type);

    return productRepository.findAll()
            .stream()
            .filter(product ->
                    product.getType().equals(type)
            )
            .collect(Collectors.toList());

}


// =========================================================
// GET ONE PAR TYPE + ID
// =========================================================

public Product getProductByTypeAndId(
        String type,
        Long id) {

    validateProductType(type);

    Product product =
            productRepository
                    .findById(id)
                    .orElseThrow(
                            () -> new RuntimeException(
                                    "Produit introuvable avec l'id : " + id
                            )
                    );

    validateProductMatchesType(
            product,
            type
    );

    return product;

}


// =========================================================
// CREATE
// =========================================================

public Product createProduct(
        ProductRequest request) {

    validateProductType(
            request.getType()
    );

    Product product =
            createProductFromRequest(request);

    return productRepository.save(product);

}


// =========================================================
// CONSTRUCTION DU PRODUIT
// =========================================================

private Product createProductFromRequest(
        ProductRequest request) {

    Product product;


    switch (request.getType()) {

        // -------------------------------------------------
        // LIVRE
        // -------------------------------------------------

        case "book":

            Book book = new Book();

            book.setIsbn(
                    request.getIsbn()
            );

            book.setAuthor(
                    request.getAuthor()
            );

            book.setPublisher(
                    request.getPublisher()
            );

            book.setNumberOfPages(
                    request.getNumberOfPages()
            );

            product = book;

            break;


        // -------------------------------------------------
        // JEU VIDÉO
        // -------------------------------------------------

        case "video-game":

            VideoGame videoGame =
                    new VideoGame();

            videoGame.setDeveloper(
                    request.getDeveloper()
            );

            videoGame.setPlatform(
                    request.getPlatform()
            );

            videoGame.setGenre(
                    request.getGenre()
            );

            videoGame.setAgeRating(
                    request.getAgeRating()
            );

            product = videoGame;

            break;


        // -------------------------------------------------
        // DVD
        // -------------------------------------------------

        case "dvd":

            Dvd dvd = new Dvd();

            dvd.setDirector(
                    request.getDirector()
            );

            dvd.setDuration(
                    request.getDuration()
            );

            dvd.setReleaseYear(
                    request.getReleaseYear()
            );

            product = dvd;

            break;


        default:

            throw new IllegalArgumentException(
                    "Type de produit invalide : "
                            + request.getType()
            );
    }


    // -----------------------------------------------------
    // CHAMPS COMMUNS
    // -----------------------------------------------------

    product.setName(
            request.getName()
    );

    product.setPrice(
            request.getPrice()
    );

    product.setDescription(
            request.getDescription()
    );


    return product;

}


// =========================================================
// UPDATE PAR TYPE + ID
// =========================================================

public Product updateProduct(
        String type,
        Long id,
        ProductRequest request) {

    validateProductType(type);

    Product existingProduct =
            getProductByTypeAndId(
                    type,
                    id
            );


    // -----------------------------------------------------
    // VÉRIFICATION DU TYPE DU BODY
    // -----------------------------------------------------

    if (
            request.getType() != null &&
            !type.equals(request.getType())
    ) {

        throw new IllegalArgumentException(
                "Le type demandé dans l'URL (" +
                        type +
                        ") ne correspond pas au type envoyé (" +
                        request.getType() +
                        ")."
        );

    }


    // -----------------------------------------------------
    // CHAMPS COMMUNS
    // -----------------------------------------------------

    existingProduct.setName(
            request.getName()
    );

    existingProduct.setPrice(
            request.getPrice()
    );

    existingProduct.setDescription(
            request.getDescription()
    );


    // -----------------------------------------------------
    // LIVRE
    // -----------------------------------------------------

    if (existingProduct instanceof Book book) {

        book.setIsbn(
                request.getIsbn()
        );

        book.setAuthor(
                request.getAuthor()
        );

        book.setPublisher(
                request.getPublisher()
        );

        book.setNumberOfPages(
                request.getNumberOfPages()
        );

    }


    // -----------------------------------------------------
    // JEU VIDÉO
    // -----------------------------------------------------

    if (existingProduct instanceof VideoGame videoGame) {

        videoGame.setDeveloper(
                request.getDeveloper()
        );

        videoGame.setPlatform(
                request.getPlatform()
        );

        videoGame.setGenre(
                request.getGenre()
        );

        videoGame.setAgeRating(
                request.getAgeRating()
        );

    }


    // -----------------------------------------------------
    // DVD
    // -----------------------------------------------------

    if (existingProduct instanceof Dvd dvd) {

        dvd.setDirector(
                request.getDirector()
        );

        dvd.setDuration(
                request.getDuration()
        );

        dvd.setReleaseYear(
                request.getReleaseYear()
        );

    }


    return productRepository.save(
            existingProduct
    );

}


// =========================================================
// DELETE PAR TYPE + ID
// =========================================================

public void deleteProduct(
        String type,
        Long id) {

    validateProductType(type);

    Product product =
            getProductByTypeAndId(
                    type,
                    id
            );

    productRepository.delete(product);

}


// =========================================================
// VALIDATION DU TYPE
// =========================================================

private void validateProductType(String type) {

    if (
            !"book".equals(type) &&
            !"video-game".equals(type) &&
            !"dvd".equals(type)
    ) {

        throw new IllegalArgumentException(
                "Type de produit invalide : " + type
        );

    }

}


// =========================================================
// VALIDATION TYPE + PRODUIT
// =========================================================

private void validateProductMatchesType(
        Product product,
        String type) {

    if (
            !product.getType().equals(type)
    ) {

        throw new IllegalArgumentException(
                "L'identifiant " +
                        product.getId() +
                        " correspond à un " +
                        product.getType() +
                        " et non à un " +
                        type +
                        "."
        );

    }

}

}