package com.example.myproject.demo.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class VideoGameEqualsHashCodeTest {

    @Test
    void twoVideoGamesWithSameValuesShouldBeEqual() {

        VideoGame videoGame1 = new VideoGame();

        videoGame1.setName("The Legend of Zelda");
        videoGame1.setPrice(new BigDecimal("59.99"));
        videoGame1.setDescription("Jeu d'aventure");
        videoGame1.setDeveloper("Nintendo");
        videoGame1.setPlatform("Nintendo Switch");
        videoGame1.setGenre("Adventure");
        videoGame1.setAgeRating("7+");

        VideoGame videoGame2 = new VideoGame();

        videoGame2.setName("The Legend of Zelda");
        videoGame2.setPrice(new BigDecimal("59.99"));
        videoGame2.setDescription("Jeu d'aventure");
        videoGame2.setDeveloper("Nintendo");
        videoGame2.setPlatform("Nintendo Switch");
        videoGame2.setGenre("Adventure");
        videoGame2.setAgeRating("7+");

        assertEquals(videoGame1, videoGame2);
    }

    @Test
    void twoVideoGamesWithSameValuesShouldHaveSameHashCode() {

        VideoGame videoGame1 = new VideoGame();

        videoGame1.setName("The Legend of Zelda");
        videoGame1.setPrice(new BigDecimal("59.99"));
        videoGame1.setDescription("Jeu d'aventure");
        videoGame1.setDeveloper("Nintendo");
        videoGame1.setPlatform("Nintendo Switch");
        videoGame1.setGenre("Adventure");
        videoGame1.setAgeRating("7+");

        VideoGame videoGame2 = new VideoGame();

        videoGame2.setName("The Legend of Zelda");
        videoGame2.setPrice(new BigDecimal("59.99"));
        videoGame2.setDescription("Jeu d'aventure");
        videoGame2.setDeveloper("Nintendo");
        videoGame2.setPlatform("Nintendo Switch");
        videoGame2.setGenre("Adventure");
        videoGame2.setAgeRating("7+");

        assertEquals(videoGame1.hashCode(), videoGame2.hashCode());
    }

    @Test
    void videoGameShouldEqualItself() {

        VideoGame videoGame = new VideoGame();

        assertEquals(videoGame, videoGame);
    }

    @Test
    void videoGameShouldNotEqualNull() {

        VideoGame videoGame = new VideoGame();

        assertNotEquals(videoGame, null);
    }

    @Test
    void videoGameShouldNotEqualAnotherType() {

        VideoGame videoGame = new VideoGame();
        Book book = new Book();

        assertNotEquals(videoGame, book);
    }

    @Test
    void changingDeveloperShouldMakeVideoGameDifferent() {

        VideoGame videoGame1 = new VideoGame();

        videoGame1.setName("The Legend of Zelda");
        videoGame1.setPrice(new BigDecimal("59.99"));
        videoGame1.setDeveloper("Nintendo");
        videoGame1.setPlatform("Nintendo Switch");
        videoGame1.setGenre("Adventure");
        videoGame1.setAgeRating("7+");

        VideoGame videoGame2 = new VideoGame();

        videoGame2.setName("The Legend of Zelda");
        videoGame2.setPrice(new BigDecimal("59.99"));
        videoGame2.setDeveloper("Sony");
        videoGame2.setPlatform("Nintendo Switch");
        videoGame2.setGenre("Adventure");
        videoGame2.setAgeRating("7+");

        assertNotEquals(videoGame1, videoGame2);
    }

    @Test
    void changingPlatformShouldMakeVideoGameDifferent() {

        VideoGame videoGame1 = new VideoGame();

        videoGame1.setName("The Legend of Zelda");
        videoGame1.setPrice(new BigDecimal("59.99"));
        videoGame1.setDeveloper("Nintendo");
        videoGame1.setPlatform("Nintendo Switch");
        videoGame1.setGenre("Adventure");
        videoGame1.setAgeRating("7+");

        VideoGame videoGame2 = new VideoGame();

        videoGame2.setName("The Legend of Zelda");
        videoGame2.setPrice(new BigDecimal("59.99"));
        videoGame2.setDeveloper("Nintendo");
        videoGame2.setPlatform("PlayStation 5");
        videoGame2.setGenre("Adventure");
        videoGame2.setAgeRating("7+");

        assertNotEquals(videoGame1, videoGame2);
    }

    @Test
    void changingGenreShouldMakeVideoGameDifferent() {

        VideoGame videoGame1 = new VideoGame();

        videoGame1.setName("The Legend of Zelda");
        videoGame1.setPrice(new BigDecimal("59.99"));
        videoGame1.setDeveloper("Nintendo");
        videoGame1.setPlatform("Nintendo Switch");
        videoGame1.setGenre("Adventure");
        videoGame1.setAgeRating("7+");

        VideoGame videoGame2 = new VideoGame();

        videoGame2.setName("The Legend of Zelda");
        videoGame2.setPrice(new BigDecimal("59.99"));
        videoGame2.setDeveloper("Nintendo");
        videoGame2.setPlatform("Nintendo Switch");
        videoGame2.setGenre("RPG");
        videoGame2.setAgeRating("7+");

        assertNotEquals(videoGame1, videoGame2);
    }

    @Test
    void changingAgeRatingShouldMakeVideoGameDifferent() {

        VideoGame videoGame1 = new VideoGame();

        videoGame1.setName("The Legend of Zelda");
        videoGame1.setPrice(new BigDecimal("59.99"));
        videoGame1.setDeveloper("Nintendo");
        videoGame1.setPlatform("Nintendo Switch");
        videoGame1.setGenre("Adventure");
        videoGame1.setAgeRating("7+");

        VideoGame videoGame2 = new VideoGame();

        videoGame2.setName("The Legend of Zelda");
        videoGame2.setPrice(new BigDecimal("59.99"));
        videoGame2.setDeveloper("Nintendo");
        videoGame2.setPlatform("Nintendo Switch");
        videoGame2.setGenre("Adventure");
        videoGame2.setAgeRating("18+");

        assertNotEquals(videoGame1, videoGame2);
    }
}
