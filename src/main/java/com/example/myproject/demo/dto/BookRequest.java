package com.example.myproject.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookRequest extends ProductRequest {

    private String isbn;

    private String author;

    private String publisher;

    private Integer numberOfPages;
}
