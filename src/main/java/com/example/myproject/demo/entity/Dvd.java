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
@ToString
public class Dvd extends Product {

    private String director;

    private Integer duration;

    private Integer releaseYear;

    @Override 
    public String getType() {
        return "dvd";
    }
}