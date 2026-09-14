package com.example.myproject.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DvdRequest extends ProductRequest {

    private String director;

    private Integer duration;

    private Integer releaseYear;
}
