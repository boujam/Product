package com.example.myproject.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
public class Book extends Product {

    // 💡 Initialisation propre du type discriminant au constructeur
    public Book() {
        super();
        this.setProductType("book");
    }

    // 💡 Sécurise l'index d'unicité SQL Server avec la validation Regex Java
    @NotBlank
    @Pattern(regexp = "^\\d{10,13}$")
    @Column(unique = true, nullable = false, length = 13)
    private String isbn;

    @NotBlank
    @Column(nullable = false)
    private String author;

    @NotBlank
    @Column(nullable = false)
    private String publisher;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Integer numberOfPages;

    @Override
    public String getProductType() {
        productType = "book";
        return productType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
        result = prime * result + ((author == null) ? 0 : author.hashCode());
        result = prime * result + ((publisher == null) ? 0 : publisher.hashCode());
        result = prime * result + ((numberOfPages == null) ? 0 : numberOfPages.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Book other = (Book) obj;
        if (isbn == null) {
            if (other.isbn != null)
                return false;
        } else if (!isbn.equals(other.isbn))
            return false;
        if (author == null) {
            if (other.author != null)
                return false;
        } else if (!author.equals(other.author))
            return false;
        if (publisher == null) {
            if (other.publisher != null)
                return false;
        } else if (!publisher.equals(other.publisher))
            return false;
        if (numberOfPages == null) {
            if (other.numberOfPages != null)
                return false;
        } else if (!numberOfPages.equals(other.numberOfPages))
            return false;
        return true;
    }

}
