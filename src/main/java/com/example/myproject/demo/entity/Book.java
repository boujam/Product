package com.example.myproject.demo.entity;

import jakarta.persistence.Column;
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
public class Book extends Product {

    @Column(unique = true)
    private String isbn;
    private String author;
    private String publisher;
    private Integer numberOfPages;
}
