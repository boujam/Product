package com.example.myproject.demo.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

/*
Book.equals() dépend de tous ces champs :

Product
├── id
├── name
├── price
└── description

Book
├── isbn
├── author
├── publisher
└── numberOfPages

Le fichier vérifie donc que :

    deux livres identiques sont égaux ;
    deux livres identiques ont le même hashCode() ;
    un livre est égal à lui-même ;
    un livre n'est pas égal à null ;
    un Book n'est pas égal à un Dvd ;
    modifier isbn rend les livres différents ;
    modifier author les rend différents ;
    modifier publisher les rend différents ;
    modifier numberOfPages les rend différents ;
    modifier les propriétés héritées (name, price, description) les rend différents. 

*/

class BookEqualsHashCodeTest {

    @Test
    void twoBooksWithSameValuesShouldBeEqual() {

        Book book1 = new Book();

        book1.setName("Le Petit Prince");
        book1.setPrice(new BigDecimal("15.90"));
        book1.setDescription("Un livre célèbre");
        book1.setIsbn("9782070612758");
        book1.setAuthor("Antoine de Saint-Exupéry");
        book1.setPublisher("Gallimard");
        book1.setNumberOfPages(96);

        Book book2 = new Book();

        book2.setName("Le Petit Prince");
        book2.setPrice(new BigDecimal("15.90"));
        book2.setDescription("Un livre célèbre");
        book2.setIsbn("9782070612758");
        book2.setAuthor("Antoine de Saint-Exupéry");
        book2.setPublisher("Gallimard");
        book2.setNumberOfPages(96);

        assertEquals(book1, book2);
    }

    @Test
    void twoBooksWithSameValuesShouldHaveSameHashCode() {

        Book book1 = new Book();

        book1.setName("Le Petit Prince");
        book1.setPrice(new BigDecimal("15.90"));
        book1.setDescription("Un livre célèbre");
        book1.setIsbn("9782070612758");
        book1.setAuthor("Antoine de Saint-Exupéry");
        book1.setPublisher("Gallimard");
        book1.setNumberOfPages(96);

        Book book2 = new Book();

        book2.setName("Le Petit Prince");
        book2.setPrice(new BigDecimal("15.90"));
        book2.setDescription("Un livre célèbre");
        book2.setIsbn("9782070612758");
        book2.setAuthor("Antoine de Saint-Exupéry");
        book2.setPublisher("Gallimard");
        book2.setNumberOfPages(96);

        assertEquals(book1.hashCode(), book2.hashCode());
    }

    /* 
    @Test
    void twoNewBooksShouldNotBeEqual() {

        Book book1 = new Book();
        Book book2 = new Book();

        assertNotEquals(book1, book2);
    }
    */

    @Test
    void booksWithSameIdShouldBeEqual() {

        Book book1 = new Book();
        Book book2 = new Book();

        book1.setId(1L);
        book2.setId(1L);

        assertEquals(book1, book2);
        assertEquals(book1.hashCode(), book2.hashCode());
    }

    @Test
    void booksWithDifferentIdsShouldNotBeEqual() {

        Book book1 = new Book();
        Book book2 = new Book();

        book1.setId(1L);
        book2.setId(2L);

        assertNotEquals(book1, book2);
    }

    @Test
    void bookShouldEqualItself() {

        Book book = new Book();

        assertEquals(book, book);
    }

    @Test
    void bookShouldNotEqualNull() {

        Book book = new Book();

        assertNotEquals(book, null);
    }

    @Test
    void bookShouldNotEqualAnotherType() {

        Book book = new Book();
        Dvd dvd = new Dvd();

        assertNotEquals(book, dvd);
    }

    @Test
    void changingIsbnShouldMakeBooksDifferent() {

        Book book1 = createBook();
        Book book2 = createBook();

        book2.setIsbn("9781234567890");

        assertNotEquals(book1, book2);
    }

    @Test
    void changingAuthorShouldMakeBooksDifferent() {

        Book book1 = createBook();
        Book book2 = createBook();

        book2.setAuthor("Victor Hugo");

        assertNotEquals(book1, book2);
    }

    @Test
    void changingPublisherShouldMakeBooksDifferent() {

        Book book1 = createBook();
        Book book2 = createBook();

        book2.setPublisher("Hachette");

        assertNotEquals(book1, book2);
    }

    @Test
    void changingNumberOfPagesShouldMakeBooksDifferent() {

        Book book1 = createBook();
        Book book2 = createBook();

        book2.setNumberOfPages(200);

        assertNotEquals(book1, book2);
    }

    @Test
    void changingNameShouldMakeBooksDifferent() {

        Book book1 = createBook();
        Book book2 = createBook();

        book2.setName("Un autre livre");

        assertNotEquals(book1, book2);
    }

    @Test
    void changingPriceShouldMakeBooksDifferent() {

        Book book1 = createBook();
        Book book2 = createBook();

        book2.setPrice(new BigDecimal("20.00"));

        assertNotEquals(book1, book2);
    }

    @Test
    void changingDescriptionShouldMakeBooksDifferent() {

        Book book1 = createBook();
        Book book2 = createBook();

        book2.setDescription("Une autre description");

        assertNotEquals(book1, book2);
    }

    private Book createBook() {

        Book book = new Book();

        book.setName("Le Petit Prince");
        book.setPrice(new BigDecimal("15.90"));
        book.setDescription("Un livre célèbre");
        book.setIsbn("9782070612758");
        book.setAuthor("Antoine de Saint-Exupéry");
        book.setPublisher("Gallimard");
        book.setNumberOfPages(96);

        return book;
    }
}