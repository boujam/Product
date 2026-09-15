package com.example.myproject.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString (callSuper = true)
public class BookRequest extends ProductRequest {

    public BookRequest() {
        super.productType = "book";
    }

    private String isbn;

    private String author;

    private String publisher;

    private Integer numberOfPages;
}
