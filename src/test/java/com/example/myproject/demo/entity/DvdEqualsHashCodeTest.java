package com.example.myproject.demo.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class DvdEqualsHashCodeTest {

    @Test
    void twoDvdWithSameValuesShouldBeEqual() {

        Dvd dvd1 = new Dvd();
        dvd1.setName("Inception");
        dvd1.setPrice(new BigDecimal("12.99"));
        dvd1.setDescription("Film de science-fiction");
        dvd1.setDirector("Christopher Nolan");
        dvd1.setDuration(148);
        dvd1.setReleaseYear(2010);

        Dvd dvd2 = new Dvd();
        dvd2.setName("Inception");
        dvd2.setPrice(new BigDecimal("12.99"));
        dvd2.setDescription("Film de science-fiction");
        dvd2.setDirector("Christopher Nolan");
        dvd2.setDuration(148);
        dvd2.setReleaseYear(2010);

        assertEquals(dvd1, dvd2);
    }

    @Test
    void twoDvdWithSameValuesShouldHaveSameHashCode() {

        Dvd dvd1 = new Dvd();
        dvd1.setName("Inception");
        dvd1.setPrice(new BigDecimal("12.99"));
        dvd1.setDescription("Film de science-fiction");
        dvd1.setDirector("Christopher Nolan");
        dvd1.setDuration(148);
        dvd1.setReleaseYear(2010);

        Dvd dvd2 = new Dvd();
        dvd2.setName("Inception");
        dvd2.setPrice(new BigDecimal("12.99"));
        dvd2.setDescription("Film de science-fiction");
        dvd2.setDirector("Christopher Nolan");
        dvd2.setDuration(148);
        dvd2.setReleaseYear(2010);

        assertEquals(dvd1.hashCode(), dvd2.hashCode());
    }

    @Test
    void dvdShouldNotEqualNull() {

        Dvd dvd = new Dvd();

        assertNotEquals(dvd, null);
    }

    @Test
    void dvdShouldEqualItself() {

        Dvd dvd = new Dvd();

        assertEquals(dvd, dvd);
    }

    @Test
    void dvdShouldNotEqualBook() {

        Dvd dvd = new Dvd();
        Book book = new Book();

        assertNotEquals(dvd, book);
    }

    @Test
    void changingDirectorShouldMakeDvdDifferent() {

        Dvd dvd1 = new Dvd();
        dvd1.setName("Inception");
        dvd1.setPrice(new BigDecimal("12.99"));
        dvd1.setDirector("Christopher Nolan");
        dvd1.setDuration(148);
        dvd1.setReleaseYear(2010);

        Dvd dvd2 = new Dvd();
        dvd2.setName("Inception");
        dvd2.setPrice(new BigDecimal("12.99"));
        dvd2.setDirector("Steven Spielberg");
        dvd2.setDuration(148);
        dvd2.setReleaseYear(2010);

        assertNotEquals(dvd1, dvd2);
    }

    @Test
    void changingDurationShouldMakeDvdDifferent() {

        Dvd dvd1 = new Dvd();
        dvd1.setName("Inception");
        dvd1.setPrice(new BigDecimal("12.99"));
        dvd1.setDirector("Christopher Nolan");
        dvd1.setDuration(148);
        dvd1.setReleaseYear(2010);

        Dvd dvd2 = new Dvd();
        dvd2.setName("Inception");
        dvd2.setPrice(new BigDecimal("12.99"));
        dvd2.setDirector("Christopher Nolan");
        dvd2.setDuration(120);
        dvd2.setReleaseYear(2010);

        assertNotEquals(dvd1, dvd2);
    }

    @Test
    void changingReleaseYearShouldMakeDvdDifferent() {

        Dvd dvd1 = new Dvd();
        dvd1.setName("Inception");
        dvd1.setPrice(new BigDecimal("12.99"));
        dvd1.setDirector("Christopher Nolan");
        dvd1.setDuration(148);
        dvd1.setReleaseYear(2010);

        Dvd dvd2 = new Dvd();
        dvd2.setName("Inception");
        dvd2.setPrice(new BigDecimal("12.99"));
        dvd2.setDirector("Christopher Nolan");
        dvd2.setDuration(148);
        dvd2.setReleaseYear(2020);

        assertNotEquals(dvd1, dvd2);
    }
}
