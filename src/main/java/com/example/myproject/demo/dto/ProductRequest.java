package com.example.myproject.demo.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "productType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BookRequest.class, name = "book"),
        @JsonSubTypes.Type(value = DvdRequest.class, name = "dvd"),
        @JsonSubTypes.Type(value = VideoGameRequest.class, name = "video-game")
})
@Getter
@Setter
@ToString 
@NoArgsConstructor
public abstract class ProductRequest {

    private String name;

    private BigDecimal price;

    private String description;

    protected String productType;
}
