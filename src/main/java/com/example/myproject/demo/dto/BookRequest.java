package com.example.myproject.demo.dto;

// 💡 Import global également pour la classe enfant
import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class BookRequest extends ProductRequest {

    public BookRequest() {
        super.productType = "book";
    }

    @NotBlank // 💡 L'ISBN est obligatoire
    @Pattern(regexp = "^\\d{10,13}$") // 💡 Version optimisée : '\\d' remplace '[0-9]' pour cibler les chiffres
    private String isbn;

    @NotBlank // 💡 L'auteur est requis
    private String author;

    @NotBlank // 💡 L'éditeur est requis
    private String publisher;

    @NotNull // 💡 Le nombre de pages est requis
    @Min(1) // 💡 Un livre doit avoir au moins 1 page
    private Integer numberOfPages;
}
