package com.example.myproject.demo.controller;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductRequest {

    private String type;
    private String name;
    private BigDecimal price;
    private String description;

    // Livre
    private String isbn;
    private String author;
    private String publisher;
    private Integer numberOfPages;

    // Jeu vidéo
    private String developer;
    private String platform;
    private String genre;
    private String ageRating;

    // DVD
    private String director;
    private Integer duration;
    private Integer releaseYear;
}