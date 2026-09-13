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
public class VideoGame extends Product {

    private String developer;

    private String platform;

    private String genre;

    private String ageRating;

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

    @Override 
    public String getType() {
        return "video-game";
    }
}