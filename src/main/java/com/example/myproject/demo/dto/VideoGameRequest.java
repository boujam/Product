package com.example.myproject.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.validation.constraints.*;

@Getter
@Setter
@ToString(callSuper = true)
public class VideoGameRequest extends ProductRequest {

    public VideoGameRequest() {
        super.productType = "video-game";
    }

    @NotBlank // Obligatoire
    private String developer;

    @NotBlank // Obligatoire (ex: PC, PS5, Switch)
    private String platform;

    @NotBlank // Obligatoire (ex: RPG, Action)
    private String genre;

    @NotBlank // Obligatoire
    @Pattern(regexp = "^PEGI (3|7|12|16|18)$") // 💡 Force un format standardisé de classification d'âge européen
    private String ageRating;
}
