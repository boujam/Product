package com.example.myproject.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VideoGameRequest extends ProductRequest {

    private String developer;

    private String platform;

    private String genre;

    private String ageRating;
}
