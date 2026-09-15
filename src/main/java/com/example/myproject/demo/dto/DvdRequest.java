package com.example.myproject.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString (callSuper = true)
public class DvdRequest extends ProductRequest {

    public DvdRequest() {
        super.productType = "dvd";
    }

    private String director;

    private Integer duration;

    private Integer releaseYear;
}
