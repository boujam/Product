package com.example.myproject.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.example.myproject.demo.dto.BookRequest;
import com.example.myproject.demo.dto.DvdRequest;
import com.example.myproject.demo.dto.ProductRequest;
import com.example.myproject.demo.dto.VideoGameRequest;
import com.example.myproject.demo.entity.Book;
import com.example.myproject.demo.entity.Dvd;
import com.example.myproject.demo.entity.Product;
import com.example.myproject.demo.entity.VideoGame;
import com.example.myproject.demo.service.ProductService;
import com.example.myproject.demo.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

        @Mock
        private ProductRepository productRepository;

        private ProductService productService;

        @BeforeEach
        void setUp() {
                productService = new ProductService(productRepository);
        }

        // =========================================================
        // GET ALL
        // =========================================================

        @Test
        void shouldReturnAllProducts() {

                Book book = createBook(1L);
                Dvd dvd = createDvd(2L);
                VideoGame videoGame = createVideoGame(3L);

                List<Product> products = List.of(book, dvd, videoGame);

                when(productRepository.findAll()).thenReturn(products);

                List<Product> result = productService.getAllProducts();

                assertEquals(3, result.size());
                assertEquals(products, result);

                verify(productRepository).findAll();
        }

        @Test
        void shouldReturnEmptyListWhenNoProductExists() {

                when(productRepository.findAll()).thenReturn(List.of());

                List<Product> result = productService.getAllProducts();

                assertNotNull(result);
                assertTrue(result.isEmpty());

                verify(productRepository).findAll();
        }

        // =========================================================
        // GET ALL PAR TYPE
        // =========================================================

        @Test
        void shouldReturnOnlyBooksWhenTypeIsBook() {

                Book book1 = createBook(1L);
                Book book2 = createBook(2L);

                Dvd dvd = createDvd(3L);
                VideoGame videoGame = createVideoGame(4L);

                when(productRepository.findAll())
                                .thenReturn(List.of(book1, book2, dvd, videoGame));

                List<Product> result = productService.getProductsByType("book");

                assertEquals(2, result.size());
                assertTrue(result.contains(book1));
                assertTrue(result.contains(book2));
                assertFalse(result.contains(dvd));
                assertFalse(result.contains(videoGame));
        }

        @Test
        void shouldReturnOnlyDvdsWhenTypeIsDvd() {

                Book book = createBook(1L);
                Dvd dvd1 = createDvd(2L);
                Dvd dvd2 = createDvd(3L);
                VideoGame videoGame = createVideoGame(4L);

                when(productRepository.findAll())
                                .thenReturn(List.of(book, dvd1, dvd2, videoGame));

                List<Product> result = productService.getProductsByType("dvd");

                assertEquals(2, result.size());
                assertTrue(result.contains(dvd1));
                assertTrue(result.contains(dvd2));
                assertFalse(result.contains(book));
                assertFalse(result.contains(videoGame));
        }

        @Test
        void shouldReturnOnlyVideoGamesWhenTypeIsVideoGame() {

                Book book = createBook(1L);
                Dvd dvd = createDvd(2L);
                VideoGame game1 = createVideoGame(3L);
                VideoGame game2 = createVideoGame(4L);

                when(productRepository.findAll())
                                .thenReturn(List.of(book, dvd, game1, game2));

                List<Product> result = productService.getProductsByType("video-game");

                assertEquals(2, result.size());
                assertTrue(result.contains(game1));
                assertTrue(result.contains(game2));
                assertFalse(result.contains(book));
                assertFalse(result.contains(dvd));
        }

        @Test
        void shouldRejectInvalidTypeWhenGettingProductsByType() {

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                () -> productService.getProductsByType("unknown"));

                assertEquals(
                                "Type de produit invalide : unknown",
                                exception.getMessage());

                verify(productRepository, never()).findAll();
        }

        @Test
        void shouldRejectNullTypeWhenGettingProductsByType() {

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                () -> productService.getProductsByType(null));

                assertEquals(
                                "Type de produit invalide : null",
                                exception.getMessage());

                verify(productRepository, never()).findAll();
        }

        // =========================================================
        // GET ONE PAR TYPE + ID
        // =========================================================

        @Test
        void shouldReturnBookWhenTypeAndIdMatch() {

                Book book = createBook(1L);

                when(productRepository.findById(1L))
                                .thenReturn(Optional.of(book));

                Product result = productService.getProductByTypeAndId("book", 1L);

                assertSame(book, result);

                verify(productRepository).findById(1L);
        }

        @Test
        void shouldReturnDvdWhenTypeAndIdMatch() {

                Dvd dvd = createDvd(2L);

                when(productRepository.findById(2L))
                                .thenReturn(Optional.of(dvd));

                Product result = productService.getProductByTypeAndId("dvd", 2L);

                assertSame(dvd, result);

                verify(productRepository).findById(2L);
        }

        @Test
        void shouldReturnVideoGameWhenTypeAndIdMatch() {

                VideoGame game = createVideoGame(3L);

                when(productRepository.findById(3L))
                                .thenReturn(Optional.of(game));

                Product result = productService.getProductByTypeAndId(
                                "video-game",
                                3L);

                assertSame(game, result);

                verify(productRepository).findById(3L);
        }

        @Test
        void shouldThrowExceptionWhenProductDoesNotExist() {

                when(productRepository.findById(99L))
                                .thenReturn(Optional.empty());

                RuntimeException exception = assertThrows(
                                RuntimeException.class,
                                () -> productService.getProductByTypeAndId(
                                                "book",
                                                99L));

                assertEquals(
                                "Produit introuvable avec l'id : 99",
                                exception.getMessage());
        }

        @Test
        void shouldRejectInvalidTypeBeforeSearchingProduct() {

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                () -> productService.getProductByTypeAndId(
                                                "magazine",
                                                1L));

                assertEquals(
                                "Type de produit invalide : magazine",
                                exception.getMessage());

                verify(productRepository, never()).findById(anyLong());
        }

        @Test
        void shouldRejectWhenIdBelongsToAnotherProductType() {

                Book book = createBook(6L);

                when(productRepository.findById(6L))
                                .thenReturn(Optional.of(book));

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                () -> productService.getProductByTypeAndId(
                                                "dvd",
                                                6L));

                assertEquals(
                                "L'identifiant 6 correspond à un book et non à un dvd.",
                                exception.getMessage());
        }

        // =========================================================
        // CREATE BOOK
        // =========================================================

        @Test
        void shouldCreateBook() {

                BookRequest request = createBookRequest();

                Book savedBook = createBook(1L);

                when(productRepository.save(any(Book.class)))
                                .thenReturn(savedBook);

                Product result = productService.createProduct(request);

                assertSame(savedBook, result);

                ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);

                verify(productRepository).save(captor.capture());

                Book book = captor.getValue();

                assertEquals(request.getName(), book.getName());
                assertEquals(request.getPrice(), book.getPrice());
                assertEquals(request.getDescription(), book.getDescription());
                assertEquals(request.getIsbn(), book.getIsbn());
                assertEquals(request.getAuthor(), book.getAuthor());
                assertEquals(request.getPublisher(), book.getPublisher());
                assertEquals(request.getNumberOfPages(), book.getNumberOfPages());
                assertEquals(request.getProductType(), book.getProductType());
        }

        // =========================================================
        // CREATE DVD
        // =========================================================

        @Test
        void shouldCreateDvd() {

                DvdRequest request = createDvdRequest();

                Dvd savedDvd = createDvd(1L);

                when(productRepository.save(any(Dvd.class)))
                                .thenReturn(savedDvd);

                Product result = productService.createProduct(request);

                assertSame(savedDvd, result);

                ArgumentCaptor<Dvd> captor = ArgumentCaptor.forClass(Dvd.class);

                verify(productRepository).save(captor.capture());

                Dvd dvd = captor.getValue();

                assertEquals(request.getName(), dvd.getName());
                assertEquals(request.getPrice(), dvd.getPrice());
                assertEquals(request.getDescription(), dvd.getDescription());
                assertEquals(request.getDirector(), dvd.getDirector());
                assertEquals(request.getDuration(), dvd.getDuration());
                assertEquals(request.getReleaseYear(), dvd.getReleaseYear());
                assertEquals(request.getProductType(), dvd.getProductType());
        }

        // =========================================================
        // CREATE VIDEO GAME
        // =========================================================

        @Test
        void shouldCreateVideoGame() {

                VideoGameRequest request = createVideoGameRequest();

                VideoGame savedGame = createVideoGame(1L);

                when(productRepository.save(any(VideoGame.class)))
                                .thenReturn(savedGame);

                Product result = productService.createProduct(request);

                assertSame(savedGame, result);

                ArgumentCaptor<VideoGame> captor = ArgumentCaptor.forClass(VideoGame.class);

                verify(productRepository).save(captor.capture());

                VideoGame game = captor.getValue();

                assertEquals(request.getName(), game.getName());
                assertEquals(request.getPrice(), game.getPrice());
                assertEquals(request.getDescription(), game.getDescription());
                assertEquals(request.getDeveloper(), game.getDeveloper());
                assertEquals(request.getPlatform(), game.getPlatform());
                assertEquals(request.getGenre(), game.getGenre());
                assertEquals(request.getAgeRating(), game.getAgeRating());
                assertEquals(request.getProductType(), game.getProductType());
        }

        private static class UnsupportedProductRequest extends ProductRequest {
        }

        @Test
        void shouldRejectUnsupportedProductRequest() {

                ProductRequest request = new UnsupportedProductRequest();

                request.setName("Produit");
                request.setPrice(BigDecimal.TEN);
                request.setDescription("Description");
                request.setProductType("unknown");

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                () -> productService.createProduct(request));

                assertEquals(
                                "Type de produit non supporté",
                                exception.getMessage());

                verify(productRepository, never()).save(any());
        }

        // =========================================================
        // UPDATE BOOK
        // =========================================================

        @Test
        void shouldUpdateBook() {

                Book existingBook = createBook(1L);

                BookRequest request = createBookRequest();
                request.setName("Nouveau livre");
                request.setPrice(new BigDecimal("25.99"));
                request.setDescription("Nouvelle description");
                request.setIsbn("9781234567890");
                request.setAuthor("Nouvel auteur");
                request.setPublisher("Nouvel éditeur");
                request.setNumberOfPages(500);

                when(productRepository.findById(1L))
                                .thenReturn(Optional.of(existingBook));

                when(productRepository.save(existingBook))
                                .thenReturn(existingBook);

                Product result = productService.updateProduct(1L, request);

                assertSame(existingBook, result);

                assertEquals("Nouveau livre", existingBook.getName());
                assertEquals(new BigDecimal("25.99"), existingBook.getPrice());
                assertEquals("Nouvelle description", existingBook.getDescription());
                assertEquals("9781234567890", existingBook.getIsbn());
                assertEquals("Nouvel auteur", existingBook.getAuthor());
                assertEquals("Nouvel éditeur", existingBook.getPublisher());
                assertEquals(500, existingBook.getNumberOfPages());

                verify(productRepository).findById(1L);
                verify(productRepository).save(existingBook);
        }

        // =========================================================
        // UPDATE DVD
        // =========================================================

        @Test
        void shouldUpdateDvd() {

                Dvd existingDvd = createDvd(2L);

                DvdRequest request = createDvdRequest();

                request.setName("Nouveau DVD");
                request.setPrice(new BigDecimal("29.99"));
                request.setDescription("Nouvelle description");
                request.setDirector("Nouveau réalisateur");
                request.setDuration(180);
                request.setReleaseYear(2025);

                when(productRepository.findById(2L))
                                .thenReturn(Optional.of(existingDvd));

                when(productRepository.save(existingDvd))
                                .thenReturn(existingDvd);

                Product result = productService.updateProduct(2L, request);

                assertSame(existingDvd, result);

                assertEquals("Nouveau DVD", existingDvd.getName());
                assertEquals(new BigDecimal("29.99"), existingDvd.getPrice());
                assertEquals("Nouvelle description", existingDvd.getDescription());
                assertEquals("Nouveau réalisateur", existingDvd.getDirector());
                assertEquals(180, existingDvd.getDuration());
                assertEquals(2025, existingDvd.getReleaseYear());

                verify(productRepository).save(existingDvd);
        }

        // =========================================================
        // UPDATE VIDEO GAME
        // =========================================================

        @Test
        void shouldUpdateVideoGame() {

                VideoGame existingGame = createVideoGame(3L);

                VideoGameRequest request = createVideoGameRequest();

                request.setName("Nouveau jeu");
                request.setPrice(new BigDecimal("49.99"));
                request.setDescription("Nouvelle description");
                request.setDeveloper("Nouveau développeur");
                request.setPlatform("PC");
                request.setGenre("RPG");
                request.setAgeRating("PEGI 18");

                when(productRepository.findById(3L))
                                .thenReturn(Optional.of(existingGame));

                when(productRepository.save(existingGame))
                                .thenReturn(existingGame);

                Product result = productService.updateProduct(3L, request);

                assertSame(existingGame, result);

                assertEquals("Nouveau jeu", existingGame.getName());
                assertEquals(new BigDecimal("49.99"), existingGame.getPrice());
                assertEquals("Nouvelle description", existingGame.getDescription());
                assertEquals("Nouveau développeur", existingGame.getDeveloper());
                assertEquals("PC", existingGame.getPlatform());
                assertEquals("RPG", existingGame.getGenre());
                assertEquals("PEGI 18", existingGame.getAgeRating());

                verify(productRepository).save(existingGame);
        }

        // =========================================================
        // UPDATE - PRODUIT INEXISTANT
        // =========================================================

        @Test
        void shouldRejectUpdateWhenProductDoesNotExist() {

                BookRequest request = createBookRequest();

                when(productRepository.findById(99L))
                                .thenReturn(Optional.empty());

                RuntimeException exception = assertThrows(
                                RuntimeException.class,
                                () -> productService.updateProduct(99L, request));

                assertEquals(
                                "Produit introuvable avec l'id : 99",
                                exception.getMessage());

                verify(productRepository, never()).save(any());
        }

        // =========================================================
        // UPDATE - MAUVAIS TYPE
        // =========================================================

        @Test
        void shouldRejectBookRequestForDvd() {

                Dvd dvd = createDvd(1L);
                BookRequest request = createBookRequest();

                when(productRepository.findById(1L))
                                .thenReturn(Optional.of(dvd));

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                () -> productService.updateProduct(1L, request));

                assertEquals(
                                "Le produit avec l'id 1 n'est pas un livre",
                                exception.getMessage());

                verify(productRepository, never()).save(any());
        }

        @Test
        void shouldRejectDvdRequestForBook() {

                Book book = createBook(1L);
                DvdRequest request = createDvdRequest();

                when(productRepository.findById(1L))
                                .thenReturn(Optional.of(book));

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                () -> productService.updateProduct(1L, request));

                assertEquals(
                                "Le produit avec l'id 1 n'est pas un DVD",
                                exception.getMessage());

                verify(productRepository, never()).save(any());
        }

        @Test
        void shouldRejectVideoGameRequestForBook() {

                Book book = createBook(1L);
                VideoGameRequest request = createVideoGameRequest();

                when(productRepository.findById(1L))
                                .thenReturn(Optional.of(book));

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                () -> productService.updateProduct(1L, request));

                assertEquals(
                                "Le produit avec l'id 1 n'est pas un jeu vidéo",
                                exception.getMessage());

                verify(productRepository, never()).save(any());
        }

        private static class UnsupportedRequest extends ProductRequest {
        }

        @Test
        void shouldRejectUnsupportedRequestDuringUpdate() {

                Product existingProduct = createBook(1L);
                ProductRequest request = new UnsupportedRequest();

                when(productRepository.findById(1L))
                                .thenReturn(Optional.of(existingProduct));

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                () -> productService.updateProduct(1L, request));

                assertEquals(
                                "Type de produit non supporté",
                                exception.getMessage());

                verify(productRepository, never()).save(any());
        }

        // =========================================================
        // DELETE
        // =========================================================

        @Test
        void shouldDeleteBook() {

                Book book = createBook(1L);

                when(productRepository.findById(1L))
                                .thenReturn(Optional.of(book));

                productService.deleteProduct("book", 1L);

                verify(productRepository).findById(1L);
                verify(productRepository).delete(book);
        }

        @Test
        void shouldDeleteDvd() {

                Dvd dvd = createDvd(2L);

                when(productRepository.findById(2L))
                                .thenReturn(Optional.of(dvd));

                productService.deleteProduct("dvd", 2L);

                verify(productRepository).delete(dvd);
        }

        @Test
        void shouldDeleteVideoGame() {

                VideoGame game = createVideoGame(3L);

                when(productRepository.findById(3L))
                                .thenReturn(Optional.of(game));

                productService.deleteProduct("video-game", 3L);

                verify(productRepository).delete(game);
        }

        @Test
        void shouldRejectDeleteWithInvalidType() {

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                () -> productService.deleteProduct("unknown", 1L));

                assertEquals(
                                "Type de produit invalide : unknown",
                                exception.getMessage());

                verify(productRepository, never()).findById(anyLong());
                verify(productRepository, never()).delete(any());
        }

        @Test
        void shouldRejectDeleteWhenProductDoesNotExist() {

                when(productRepository.findById(99L))
                                .thenReturn(Optional.empty());

                RuntimeException exception = assertThrows(
                                RuntimeException.class,
                                () -> productService.deleteProduct("book", 99L));

                assertEquals(
                                "Produit introuvable avec l'id : 99",
                                exception.getMessage());

                verify(productRepository, never()).delete(any());
        }

        @Test
        void shouldRejectDeleteWhenTypeDoesNotMatchProduct() {

                Book book = createBook(6L);

                when(productRepository.findById(6L))
                                .thenReturn(Optional.of(book));

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                () -> productService.deleteProduct("dvd", 6L));

                assertEquals(
                                "L'identifiant 6 correspond à un book et non à un dvd.",
                                exception.getMessage());

                verify(productRepository, never()).delete(any());
        }

        // =========================================================
        // HELPERS - ENTITIES
        // =========================================================

        private Book createBook(Long id) {

                Book book = new Book();

                book.setId(id);
                book.setName("Livre test");
                book.setPrice(new BigDecimal("19.99"));
                book.setDescription("Description du livre");
                book.setProductType("book");

                book.setIsbn("9781234567890");
                book.setAuthor("Auteur test");
                book.setPublisher("Editeur test");
                book.setNumberOfPages(300);

                return book;
        }

        private Dvd createDvd(Long id) {

                Dvd dvd = new Dvd();

                dvd.setId(id);
                dvd.setName("DVD test");
                dvd.setPrice(new BigDecimal("14.99"));
                dvd.setDescription("Description du DVD");
                dvd.setProductType("dvd");

                dvd.setDirector("Réalisateur test");
                dvd.setDuration(120);
                dvd.setReleaseYear(2020);

                return dvd;
        }

        private VideoGame createVideoGame(Long id) {

                VideoGame game = new VideoGame();

                game.setId(id);
                game.setName("Jeu test");
                game.setPrice(new BigDecimal("49.99"));
                game.setDescription("Description du jeu");
                game.setProductType("video-game");

                game.setDeveloper("Développeur test");
                game.setPlatform("PC");
                game.setGenre("RPG");
                game.setAgeRating("PEGI 12");

                return game;
        }

        // =========================================================
        // HELPERS - REQUESTS
        // =========================================================

        private BookRequest createBookRequest() {

                BookRequest request = new BookRequest();

                request.setName("Livre request");
                request.setPrice(new BigDecimal("19.99"));
                request.setDescription("Description request");
                request.setProductType("book");

                request.setIsbn("9781234567890");
                request.setAuthor("Auteur request");
                request.setPublisher("Editeur request");
                request.setNumberOfPages(300);

                return request;
        }

        private DvdRequest createDvdRequest() {

                DvdRequest request = new DvdRequest();

                request.setName("DVD request");
                request.setPrice(new BigDecimal("14.99"));
                request.setDescription("Description request");
                request.setProductType("dvd");

                request.setDirector("Réalisateur request");
                request.setDuration(120);
                request.setReleaseYear(2020);

                return request;
        }

        private VideoGameRequest createVideoGameRequest() {

                VideoGameRequest request = new VideoGameRequest();

                request.setName("Jeu request");
                request.setPrice(new BigDecimal("49.99"));
                request.setDescription("Description request");
                request.setProductType("video-game");

                request.setDeveloper("Développeur request");
                request.setPlatform("PC");
                request.setGenre("RPG");
                request.setAgeRating("PEGI 12");

                return request;
        }
}