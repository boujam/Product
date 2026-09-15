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
@ToString(callSuper = true)

public class Dvd extends Product {

    private String director;

    private Integer duration;

    private Integer releaseYear;

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

    @Override
    public String getProductType() {
        productType = "dvd";
        return productType;
    }
}