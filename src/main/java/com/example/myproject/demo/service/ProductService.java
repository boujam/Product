package com.example.myproject.demo.service;

import com.example.myproject.demo.dto.BookRequest;
import com.example.myproject.demo.dto.DvdRequest;
import com.example.myproject.demo.dto.ProductRequest;
import com.example.myproject.demo.dto.VideoGameRequest;
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
                                .filter(product -> product.getProductType().equals(type))
                                .collect(Collectors.toList());

        }

        // =========================================================
        // GET ONE PAR TYPE + ID
        // =========================================================

        public Product getProductByTypeAndId(
                        String type,
                        Long id) {

                validateProductType(type);

                Product product = productRepository
                                .findById(id)
                                .orElseThrow(
                                                () -> new RuntimeException(
                                                                "Produit introuvable avec l'id : " + id));

                validateProductMatchesType(
                                product,
                                type);

                return product;

        }

        // =========================================================
        // CREATE
        // =========================================================

        public Product createProduct(ProductRequest request) {


                System.out.println("request avant sauvegarde = " + request.toString());


                if (request instanceof BookRequest bookRequest) {

                        Book book = new Book();
                        
                        book.setProductType(bookRequest.getProductType());
                        book.setName(bookRequest.getName());
                        book.setPrice(bookRequest.getPrice());
                        book.setDescription(bookRequest.getDescription());

                        book.setIsbn(bookRequest.getIsbn());
                        book.setAuthor(bookRequest.getAuthor());
                        book.setPublisher(bookRequest.getPublisher());
                        book.setNumberOfPages(bookRequest.getNumberOfPages());

                        return productRepository.save(book);
                }

                if (request instanceof DvdRequest dvdRequest) {

                        Dvd dvd = new Dvd();

                        dvd.setProductType(dvdRequest.getProductType());
                        dvd.setName(dvdRequest.getName());
                        dvd.setPrice(dvdRequest.getPrice());
                        dvd.setDescription(dvdRequest.getDescription());

                        dvd.setDirector(dvdRequest.getDirector());
                        dvd.setDuration(dvdRequest.getDuration());
                        dvd.setReleaseYear(dvdRequest.getReleaseYear());

                        return productRepository.save(dvd);
                }

                if (request instanceof VideoGameRequest videoGameRequest) {

                        VideoGame videoGame = new VideoGame();

                        videoGame.setProductType(videoGameRequest.getProductType());
                        videoGame.setName(videoGameRequest.getName());
                        videoGame.setPrice(videoGameRequest.getPrice());
                        videoGame.setDescription(videoGameRequest.getDescription());

                        videoGame.setDeveloper(videoGameRequest.getDeveloper());
                        videoGame.setPlatform(videoGameRequest.getPlatform());
                        videoGame.setGenre(videoGameRequest.getGenre());
                        videoGame.setAgeRating(videoGameRequest.getAgeRating());

                        return productRepository.save(videoGame);
                }

                throw new IllegalArgumentException(
                                "Type de produit non supporté");
        }

        // =========================================================
        // UPDATE
        // =========================================================
        public Product updateProduct(
                        Long id,
                        ProductRequest request) {

                Product existingProduct = productRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Produit introuvable avec l'id : " + id));

                // =========================================================
                // UPDATE BOOK
                // =========================================================

                if (request instanceof BookRequest bookRequest) {

                        if (!(existingProduct instanceof Book)) {
                                throw new IllegalArgumentException(
                                                "Le produit avec l'id " + id
                                                                + " n'est pas un livre");
                        }

                        Book book = (Book) existingProduct;

                        book.setName(bookRequest.getName());
                        book.setPrice(bookRequest.getPrice());
                        book.setDescription(bookRequest.getDescription());

                        book.setIsbn(bookRequest.getIsbn());
                        book.setAuthor(bookRequest.getAuthor());
                        book.setPublisher(bookRequest.getPublisher());
                        book.setNumberOfPages(bookRequest.getNumberOfPages());

                        return productRepository.save(book);
                }

                // =========================================================
                // UPDATE DVD
                // =========================================================

                if (request instanceof DvdRequest dvdRequest) {

                        if (!(existingProduct instanceof Dvd)) {
                                throw new IllegalArgumentException(
                                                "Le produit avec l'id " + id
                                                                + " n'est pas un DVD");
                        }

                        Dvd dvd = (Dvd) existingProduct;

                        dvd.setName(dvdRequest.getName());
                        dvd.setPrice(dvdRequest.getPrice());
                        dvd.setDescription(dvdRequest.getDescription());

                        dvd.setDirector(dvdRequest.getDirector());
                        dvd.setDuration(dvdRequest.getDuration());
                        dvd.setReleaseYear(dvdRequest.getReleaseYear());

                        return productRepository.save(dvd);
                }

                // =========================================================
                // UPDATE VIDEO GAME
                // =========================================================

                if (request instanceof VideoGameRequest videoGameRequest) {

                        if (!(existingProduct instanceof VideoGame)) {
                                throw new IllegalArgumentException(
                                                "Le produit avec l'id " + id
                                                                + " n'est pas un jeu vidéo");
                        }

                        VideoGame videoGame = (VideoGame) existingProduct;

                        videoGame.setName(videoGameRequest.getName());
                        videoGame.setPrice(videoGameRequest.getPrice());
                        videoGame.setDescription(videoGameRequest.getDescription());

                        videoGame.setDeveloper(videoGameRequest.getDeveloper());
                        videoGame.setPlatform(videoGameRequest.getPlatform());
                        videoGame.setGenre(videoGameRequest.getGenre());
                        videoGame.setAgeRating(videoGameRequest.getAgeRating());

                        return productRepository.save(videoGame);
                }

                throw new IllegalArgumentException(
                                "Type de produit non supporté");
        }

        // =========================================================
        // DELETE PAR TYPE + ID
        // =========================================================

        public void deleteProduct(
                        String type,
                        Long id) {

                validateProductType(type);

                Product product = getProductByTypeAndId(
                                type,
                                id);

                productRepository.delete(product);

        }

        // =========================================================
        // VALIDATION DU TYPE
        // =========================================================

        private void validateProductType(String type) {

                if (!"book".equals(type) &&
                                !"video-game".equals(type) &&
                                !"dvd".equals(type)) {

                        throw new IllegalArgumentException(
                                        "Type de produit invalide : " + type);

                }

        }

        // =========================================================
        // VALIDATION TYPE + PRODUIT
        // =========================================================

        private void validateProductMatchesType(
                        Product product,
                        String type) {

                if (!product.getProductType().equals(type)) {

                        throw new IllegalArgumentException(
                                        "L'identifiant " +
                                                        product.getId() +
                                                        " correspond à un " +
                                                        product.getProductType() +
                                                        " et non à un " +
                                                        type +
                                                        ".");

                }

        }

}