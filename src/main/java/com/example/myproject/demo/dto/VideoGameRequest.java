package com.example.myproject.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString (callSuper = true)
public class VideoGameRequest extends ProductRequest {

    public VideoGameRequest() {
        super.productType = "video-game";
    }

    private String developer;

    private String platform;

    private String genre;

    private String ageRating;
}
