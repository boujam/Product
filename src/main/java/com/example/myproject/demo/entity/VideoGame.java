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
public class VideoGame extends Product {

  // 💡 Initialisation automatique du type polymorphe au constructeur
    public VideoGame() {
        super();
        this.setProductType("video-game");
    }

    @NotBlank
    @Column(nullable = false)
    private String developer;

    @NotBlank
    @Column(nullable = false)
    private String platform;

    @NotBlank
    @Column(nullable = false)
    private String genre;

    @NotBlank
    // 💡 Aligné sur la règle de validation européenne PEGI du DTO
    @Pattern(regexp = "^PEGI (3|7|12|16|18)$")
    @Column(nullable = false, name = "age_rating", length = 7)
    private String ageRating;


    @Override
    public String getProductType() {
        productType = "video-game";
        return productType;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((developer == null) ? 0 : developer.hashCode());
        result = prime * result + ((platform == null) ? 0 : platform.hashCode());
        result = prime * result + ((genre == null) ? 0 : genre.hashCode());
        result = prime * result + ((ageRating == null) ? 0 : ageRating.hashCode());
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
        VideoGame other = (VideoGame) obj;
        if (developer == null) {
            if (other.developer != null)
                return false;
        } else if (!developer.equals(other.developer))
            return false;
        if (platform == null) {
            if (other.platform != null)
                return false;
        } else if (!platform.equals(other.platform))
            return false;
        if (genre == null) {
            if (other.genre != null)
                return false;
        } else if (!genre.equals(other.genre))
            return false;
        if (ageRating == null) {
            if (other.ageRating != null)
                return false;
        } else if (!ageRating.equals(other.ageRating))
            return false;
        return true;
    }

}