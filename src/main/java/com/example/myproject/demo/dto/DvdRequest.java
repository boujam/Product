package com.example.myproject.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.validation.constraints.*;

@Getter
@Setter
@ToString (callSuper = true)
public class DvdRequest extends ProductRequest {

    public DvdRequest() {
        super.productType = "dvd";
    }

    @NotBlank // Obligatoire
    private String director;

    @NotNull // Obligatoire
    @Min(1) // 💡 Un film dure au moins 1 minute
    @Max(600) // 💡 Limite optionnelle de sécurité à 10 heures de film
    private Integer duration;

    @NotNull // Obligatoire
    @Min(1888) // 💡 L'année du tout premier film de l'histoire du cinéma
    private Integer releaseYear;
}
