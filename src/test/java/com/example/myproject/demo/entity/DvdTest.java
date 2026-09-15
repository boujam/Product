package com.example.myproject.demo.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class DvdTest {

    @Test
    void shouldCreateDvd() {

        Dvd dvd = new Dvd();

        assertNotNull(dvd);
    }

    @Test
    void shouldSetAndGetProductProperties() {

        Dvd dvd = new Dvd();

        dvd.setName("Inception");
        dvd.setPrice(new BigDecimal("12.99"));
        dvd.setDescription("Film de science-fiction");

        assertEquals("Inception", dvd.getName());
        assertEquals(
                new BigDecimal("12.99"),
                dvd.getPrice()
        );
        assertEquals(
                "Film de science-fiction",
                dvd.getDescription()
        );
    }

    @Test
    void shouldSetAndGetDvdProperties() {

        Dvd dvd = new Dvd();

        dvd.setDirector("Christopher Nolan");
        dvd.setDuration(148);
        dvd.setReleaseYear(2010);

        assertEquals("Christopher Nolan", dvd.getDirector());
        assertEquals(148, dvd.getDuration());
        assertEquals(2010, dvd.getReleaseYear());
    }

    @Test
    void shouldReturnDvdType() {

        Dvd dvd = new Dvd();

        assertEquals("dvd", dvd.getProductType());
    }

    @Test
    void shouldBeAProduct() {

        Dvd dvd = new Dvd();

        assertTrue(dvd instanceof Product);
    }
}
