package com.example.myproject.demo.entity;

import jakarta.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class VideoGame extends Product {

    private String developer;

    private String platform;

    private String genre;

    private String ageRating;
}