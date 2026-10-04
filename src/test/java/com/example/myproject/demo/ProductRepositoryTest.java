package com.example.myproject.demo;

import com.example.myproject.demo.repository.ProductRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import com.example.myproject.demo.entity.Book;
import com.example.myproject.demo.entity.Dvd;
import com.example.myproject.demo.entity.Product;
import com.example.myproject.demo.entity.VideoGame;

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Book book;
    private Dvd dvd;
    private VideoGame videoGame;

    @BeforeEach
    void setUp() {

        productRepository.deleteAll();

        book = new Book();
        book.setName("Le Seigneur des Anneaux");
        book.setPrice(new BigDecimal("29.99"));
        book.setDescription("Un grand classique de la littérature.");
        book.setIsbn("9780261103303");
        book.setAuthor("J.R.R. Tolkien");
        book.setPublisher("HarperCollins");
        book.setNumberOfPages(500);

        dvd = new Dvd();
        dvd.setName("Inception");
        dvd.setPrice(new BigDecimal("14.99"));
        dvd.setDescription("Film de science-fiction.");
        dvd.setDirector("Christopher Nolan");
        dvd.setDuration(148);
        dvd.setReleaseYear(2010);

        videoGame = new VideoGame();
        videoGame.setName("The Witcher 3");
        videoGame.setPrice(new BigDecimal("39.99"));
        videoGame.setDescription("Jeu de rôle.");
        videoGame.setDeveloper("CD Projekt Red");
        videoGame.setPlatform("PC");
        videoGame.setGenre("RPG");
        videoGame.setAgeRating("PEGI 18");
    }

    // =========================================================
    // SAVE
    // =========================================================

    @Test
    void shouldSaveBook() {

        Product saved = productRepository.save(book);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();

        assertThat(saved).isInstanceOf(Book.class);

        Book savedBook = (Book) saved;

        assertThat(savedBook.getName())
                .isEqualTo("Le Seigneur des Anneaux");

        assertThat(savedBook.getPrice())
                .isEqualByComparingTo("29.99");

        assertThat(savedBook.getIsbn())
                .isEqualTo("9780261103303");

        assertThat(savedBook.getAuthor())
                .isEqualTo("J.R.R. Tolkien");
    }

    @Test
    void shouldSaveDvd() {

        Product saved = productRepository.save(dvd);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();

        assertThat(saved).isInstanceOf(Dvd.class);

        Dvd savedDvd = (Dvd) saved;

        assertThat(savedDvd.getName())
                .isEqualTo("Inception");

        assertThat(savedDvd.getDirector())
                .isEqualTo("Christopher Nolan");

        assertThat(savedDvd.getDuration())
                .isEqualTo(148);

        assertThat(savedDvd.getReleaseYear())
                .isEqualTo(2010);
    }

    @Test
    void shouldSaveVideoGame() {

        Product saved = productRepository.save(videoGame);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();

        assertThat(saved).isInstanceOf(VideoGame.class);

        VideoGame savedGame = (VideoGame) saved;

        assertThat(savedGame.getName())
                .isEqualTo("The Witcher 3");

        assertThat(savedGame.getDeveloper())
                .isEqualTo("CD Projekt Red");

        assertThat(savedGame.getPlatform())
                .isEqualTo("PC");

        assertThat(savedGame.getGenre())
                .isEqualTo("RPG");

        assertThat(savedGame.getAgeRating())
                .isEqualTo("PEGI 18");
    }

    // =========================================================
    // SAVE ALL
    // =========================================================

    @Test
    void shouldSaveAllProducts() {

        List<Product> products = List.of(
                book,
                dvd,
                videoGame
        );

        List<Product> savedProducts =
                productRepository.saveAll(products);

        assertThat(savedProducts)
                .hasSize(3);

        assertThat(savedProducts)
                .allSatisfy(product ->
                        assertThat(product.getId()).isNotNull()
                );
    }

    // =========================================================
    // FIND BY ID
    // =========================================================

    @Test
    void shouldFindProductById() {

        Product saved = productRepository.save(book);

        Optional<Product> result =
                productRepository.findById(saved.getId());

        assertThat(result).isPresent();

        Product found = result.get();

        assertThat(found)
                .isInstanceOf(Book.class);

        assertThat(found.getName())
                .isEqualTo("Le Seigneur des Anneaux");
    }

    @Test
    void shouldReturnEmptyWhenProductDoesNotExist() {

        Optional<Product> result =
                productRepository.findById(999999L);

        assertThat(result)
                .isEmpty();
    }

    // =========================================================
    // FIND ALL
    // =========================================================

    @Test
    void shouldFindAllProducts() {

        productRepository.save(book);
        productRepository.save(dvd);
        productRepository.save(videoGame);

        List<Product> products =
                productRepository.findAll();

        assertThat(products)
                .hasSize(3);
    }

    @Test
    void shouldFindAllProductsWithCorrectTypes() {

        productRepository.save(book);
        productRepository.save(dvd);
        productRepository.save(videoGame);

        List<Product> products =
                productRepository.findAll();

        assertThat(products)
                .anyMatch(Book.class::isInstance);

        assertThat(products)
                .anyMatch(Dvd.class::isInstance);

        assertThat(products)
                .anyMatch(VideoGame.class::isInstance);
    }

    // =========================================================
    // FIND ALL BY ID
    // =========================================================

    @Test
    void shouldFindAllProductsByIds() {

        Product savedBook = productRepository.save(book);
        Product savedDvd = productRepository.save(dvd);
        Product savedGame = productRepository.save(videoGame);

        List<Product> products =
                productRepository.findAllById(
                        List.of(
                                savedBook.getId(),
                                savedDvd.getId(),
                                savedGame.getId()
                        )
                );

        assertThat(products)
                .hasSize(3);
    }

    // =========================================================
    // EXISTS BY ID
    // =========================================================

    @Test
    void shouldReturnTrueWhenProductExists() {

        Product saved = productRepository.save(book);

        boolean exists =
                productRepository.existsById(saved.getId());

        assertThat(exists)
                .isTrue();
    }

    @Test
    void shouldReturnFalseWhenProductDoesNotExist() {

        boolean exists =
                productRepository.existsById(999999L);

        assertThat(exists)
                .isFalse();
    }

    // =========================================================
    // COUNT
    // =========================================================

    @Test
    void shouldCountProducts() {

        productRepository.save(book);
        productRepository.save(dvd);
        productRepository.save(videoGame);

        long count =
                productRepository.count();

        assertThat(count)
                .isEqualTo(3);
    }

    @Test
    void shouldReturnZeroWhenRepositoryIsEmpty() {

        long count =
                productRepository.count();

        assertThat(count)
                .isZero();
    }

    // =========================================================
    // DELETE BY ID
    // =========================================================

    @Test
    void shouldDeleteProductById() {

        Product saved =
                productRepository.save(book);

        Long id = saved.getId();

        productRepository.deleteById(id);

        assertThat(productRepository.existsById(id))
                .isFalse();
    }

    // =========================================================
    // DELETE
    // =========================================================

    @Test
    void shouldDeleteProduct() {

        Product saved =
                productRepository.save(book);

        Long id = saved.getId();

        productRepository.delete(saved);

        assertThat(productRepository.existsById(id))
                .isFalse();
    }

    // =========================================================
    // DELETE ALL
    // =========================================================

    @Test
    void shouldDeleteAllProducts() {

        productRepository.save(book);
        productRepository.save(dvd);
        productRepository.save(videoGame);

        assertThat(productRepository.count())
                .isEqualTo(3);

        productRepository.deleteAll();

        assertThat(productRepository.count())
                .isZero();
    }

    // =========================================================
    // SORT
    // =========================================================

    @Test
    void shouldFindAllProductsSortedByName() {

        productRepository.save(book);
        productRepository.save(dvd);
        productRepository.save(videoGame);

        List<Product> products =
                productRepository.findAll(
                        Sort.by(
                                Sort.Direction.ASC,
                                "name"
                        )
                );

        assertThat(products)
                .extracting(Product::getName)
                .containsExactly(
                        "Inception",
                        "Le Seigneur des Anneaux",
                        "The Witcher 3"
                );
    }

    // =========================================================
    // PAGINATION
    // =========================================================

    @Test
    void shouldReturnPaginatedProducts() {

        productRepository.save(book);
        productRepository.save(dvd);
        productRepository.save(videoGame);

        Page<Product> page =
                productRepository.findAll(
                        PageRequest.of(0, 2)
                );

        assertThat(page.getContent())
                .hasSize(2);

        assertThat(page.getTotalElements())
                .isEqualTo(3);

        assertThat(page.getTotalPages())
                .isEqualTo(2);
    }

    // =========================================================
    // FLUSH
    // =========================================================

    @Test
    void shouldFlushChanges() {

        Product saved =
                productRepository.save(book);

        productRepository.flush();

        assertThat(saved.getId())
                .isNotNull();

        assertThat(productRepository.existsById(saved.getId()))
                .isTrue();
    }

    // =========================================================
    // SAVE AND FLUSH
    // =========================================================

    @Test
    void shouldSaveAndFlushProduct() {

        Product saved =
                productRepository.saveAndFlush(book);

        assertThat(saved)
                .isNotNull();

        assertThat(saved.getId())
                .isNotNull();

        Optional<Product> result =
                productRepository.findById(saved.getId());

        assertThat(result)
                .isPresent();

        assertThat(result.get())
                .isInstanceOf(Book.class);
    }

    // =========================================================
    // DELETE ALL IN BATCH
    // =========================================================

    @Test
    void shouldDeleteAllProductsInBatch() {

        productRepository.save(book);
        productRepository.save(dvd);
        productRepository.save(videoGame);

        assertThat(productRepository.count())
                .isEqualTo(3);

        productRepository.deleteAllInBatch();

        assertThat(productRepository.count())
                .isZero();
    }

    // =========================================================
    // GET REFERENCE BY ID
    // =========================================================

    @Test
    void shouldGetReferenceById() {

        Product saved =
                productRepository.save(book);

        Product reference =
                productRepository.getReferenceById(
                        saved.getId()
                );

        assertThat(reference)
                .isNotNull();

        assertThat(reference.getId())
                .isEqualTo(saved.getId());

        assertThat(reference)
                .isInstanceOf(Book.class);
    }

    // =========================================================
    // HERITAGE JOINED
    // =========================================================

    @Test
    void shouldPersistAllInheritanceTypesInJoinedStrategy() {

        Product savedBook =
                productRepository.save(book);

        Product savedDvd =
                productRepository.save(dvd);

        Product savedGame =
                productRepository.save(videoGame);

        productRepository.flush();

        Product foundBook =
                productRepository.findById(savedBook.getId()).orElseThrow();

        Product foundDvd =
                productRepository.findById(savedDvd.getId()).orElseThrow();

        Product foundGame =
                productRepository.findById(savedGame.getId()).orElseThrow();

        assertThat(foundBook)
                .isInstanceOf(Book.class);

        assertThat(foundDvd)
                .isInstanceOf(Dvd.class);

        assertThat(foundGame)
                .isInstanceOf(VideoGame.class);
    }

    // =========================================================
    // POLYMORPHISME
    // =========================================================

    @Test
    void shouldRestoreBookSpecificFields() {

        Product saved =
                productRepository.save(book);

        Product result =
                productRepository.findById(saved.getId())
                        .orElseThrow();

        assertThat(result)
                .isInstanceOf(Book.class);

        Book found =
                (Book) result;

        assertThat(found.getIsbn())
                .isEqualTo("9780261103303");

        assertThat(found.getAuthor())
                .isEqualTo("J.R.R. Tolkien");

        assertThat(found.getPublisher())
                .isEqualTo("HarperCollins");

        assertThat(found.getNumberOfPages())
                .isEqualTo(500);
    }

    @Test
    void shouldRestoreDvdSpecificFields() {

        Product saved =
                productRepository.save(dvd);

        Product result =
                productRepository.findById(saved.getId())
                        .orElseThrow();

        assertThat(result)
                .isInstanceOf(Dvd.class);

        Dvd found =
                (Dvd) result;

        assertThat(found.getDirector())
                .isEqualTo("Christopher Nolan");

        assertThat(found.getDuration())
                .isEqualTo(148);

        assertThat(found.getReleaseYear())
                .isEqualTo(2010);
    }

    @Test
    void shouldRestoreVideoGameSpecificFields() {

        Product saved =
                productRepository.save(videoGame);

        Product result =
                productRepository.findById(saved.getId())
                        .orElseThrow();

        assertThat(result)
                .isInstanceOf(VideoGame.class);

        VideoGame found =
                (VideoGame) result;

        assertThat(found.getDeveloper())
                .isEqualTo("CD Projekt Red");

        assertThat(found.getPlatform())
                .isEqualTo("PC");

        assertThat(found.getGenre())
                .isEqualTo("RPG");

        assertThat(found.getAgeRating())
                .isEqualTo("PEGI 18");
    }

    // =========================================================
    // UPDATE
    // =========================================================

    @Test
    void shouldUpdateProduct() {

        Product saved =
                productRepository.save(book);

        saved.setName("Nouveau nom");
        saved.setPrice(new BigDecimal("49.99"));

        productRepository.save(saved);
        productRepository.flush();

        Product result =
                productRepository.findById(saved.getId())
                        .orElseThrow();

        assertThat(result.getName())
                .isEqualTo("Nouveau nom");

        assertThat(result.getPrice())
                .isEqualByComparingTo("49.99");
    }
}
