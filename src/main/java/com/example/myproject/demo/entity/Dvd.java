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

public class Dvd extends Product {

    // 💡 Initialisation automatique du type polymorphe au constructeur
    public Dvd() {
        super();
        this.setProductType("dvd");
    }

    @NotBlank
    @Column(nullable = false)
    private String director;

    @NotNull
    @Min(1)
    @Max(600) // Limite de 10 heures
    @Column(nullable = false)
    private Integer duration;

    @NotNull
    @Min(1888) // Année du premier film
    @Column(nullable = false, name = "release_year")
    private Integer releaseYear;

    @Override
    public String getProductType() {
        productType = "dvd";
        return productType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((director == null) ? 0 : director.hashCode());
        result = prime * result + ((duration == null) ? 0 : duration.hashCode());
        result = prime * result + ((releaseYear == null) ? 0 : releaseYear.hashCode());
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
        Dvd other = (Dvd) obj;
        if (director == null) {
            if (other.director != null)
                return false;
        } else if (!director.equals(other.director))
            return false;
        if (duration == null) {
            if (other.duration != null)
                return false;
        } else if (!duration.equals(other.duration))
            return false;
        if (releaseYear == null) {
            if (other.releaseYear != null)
                return false;
        } else if (!releaseYear.equals(other.releaseYear))
            return false;
        return true;
    }

}