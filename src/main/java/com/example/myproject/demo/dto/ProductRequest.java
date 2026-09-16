package com.example.myproject.demo.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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

        private Long id;
    
        @NotBlank // 💡 Requis, interdit null, les chaînes vides "" ou remplies d'espaces "   "
        @Size(min = 1, max = 100) // 💡 Limite la taille du nom entre 1 et 100 caractères
        private String name;

        @NotNull // 💡 Le prix est obligatoire
        @PositiveOrZero // 💡 Règle de gestion : Interdit les valeurs négatives (équivalent moderne à @Min(0))        
        private BigDecimal price;
        
        @Size(max = 1000) // 💡 Limite la description à 1000 caractères maximum
        private String description;

        // info utile @JsonIgnore 💡 Dit à Jackson de ne PAS inclure cette méthode dans le JSON final
        // ici n'a aucun effet sur le code car pas utilisé
        protected String productType;
        
}




