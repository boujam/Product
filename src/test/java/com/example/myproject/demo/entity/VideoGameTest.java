package com.example.myproject.demo.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class VideoGameTest {

    @Test
    void shouldCreateVideoGame() {

        VideoGame videoGame = new VideoGame();

        assertNotNull(videoGame);
    }

    @Test
    void shouldSetAndGetProductProperties() {

        VideoGame videoGame = new VideoGame();

        videoGame.setName("The Legend of Zelda");
        videoGame.setPrice(new BigDecimal("59.99"));
        videoGame.setDescription("Jeu d'aventure");

        assertEquals("The Legend of Zelda", videoGame.getName());
        assertEquals(
                new BigDecimal("59.99"),
                videoGame.getPrice()
        );
        assertEquals(
                "Jeu d'aventure",
                videoGame.getDescription()
        );
    }

    @Test
    void shouldSetAndGetVideoGameProperties() {

        VideoGame videoGame = new VideoGame();

        videoGame.setDeveloper("Nintendo");
        videoGame.setPlatform("Nintendo Switch");
        videoGame.setGenre("Adventure");
        videoGame.setAgeRating("7+");

        assertEquals("Nintendo", videoGame.getDeveloper());
        assertEquals("Nintendo Switch", videoGame.getPlatform());
        assertEquals("Adventure", videoGame.getGenre());
        assertEquals("7+", videoGame.getAgeRating());
    }

    @Test
    void shouldReturnVideoGameType() {

        VideoGame videoGame = new VideoGame();

        assertEquals("video-game", videoGame.getProductType());
    }

    @Test
    void shouldBeAProduct() {

        VideoGame videoGame = new VideoGame();

        assertTrue(videoGame instanceof Product);
    }
}
