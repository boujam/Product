package com.example.myproject.demo.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class BookTest {

    @Test
    void shouldCreateBook() {

        Book book = new Book();

        assertNotNull(book);
    }

    @Test
    void shouldSetAndGetProductProperties() {

        Book book = new Book();

        book.setName("Le Petit Prince");
        book.setPrice(new BigDecimal("15.90"));
        book.setDescription("Un livre célèbre");

        assertEquals("Le Petit Prince", book.getName());
        assertEquals(new BigDecimal("15.90"), book.getPrice());
        assertEquals("Un livre célèbre", book.getDescription());
    }

    @Test
    void shouldSetAndGetBookProperties() {

        Book book = new Book();

        book.setIsbn("9782070612758");
        book.setAuthor("Antoine de Saint-Exupéry");
        book.setPublisher("Gallimard");
        book.setNumberOfPages(96);

        assertEquals("9782070612758", book.getIsbn());
        assertEquals("Antoine de Saint-Exupéry", book.getAuthor());
        assertEquals("Gallimard", book.getPublisher());
        assertEquals(96, book.getNumberOfPages());
    }

    @Test
    void shouldReturnBookType() {

        Book book = new Book();

        assertEquals("book", book.getType());
    }

    @Test
    void shouldBeAProduct() {

        Book book = new Book();

        assertTrue(book instanceof Product);
    }
}

