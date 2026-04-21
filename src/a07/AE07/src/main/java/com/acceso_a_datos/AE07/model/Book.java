package com.acceso_a_datos.AE07.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection="books")
public class Book {

    @Id
    @Setter(AccessLevel.NONE)
    private String id;
    
    @NotBlank(message = "El titulo no puede estar vacio")
    @Size(max = 100, message = "El titulo no puede superar 100 caracteres")
    private String title;
    
    @NotBlank(message = "El titulo no puede estar vacio")
    @Size(max = 100, message = "El autor no puede superar 100 caracteres")
    private String author;

    @NotNull(message = "El genero no puede estar vacio")
    private Genre genre;

    @NotNull(message = "El precio no puede estar vacio")
    @Positive(message = "El precio debe ser mayor que 0")
    private Double price;

    @NotNull(message = "El campo de disponibilidad no puede estar vacio")
    private Boolean isAvailable;
    
    @NotNull(message = "Las páginas no pueden ser null")
    @Min(value = 2, message = "El número de páginas debe ser mayor que 1") // Valor de ejemplo, irrelevante para la practica
    private Integer pages;
    
    @JsonIgnore
    @Builder.Default
    private Boolean deleted = false;
    
    @JsonIgnore
    @Builder.Default
    private Instant deletedAt = null;
}
