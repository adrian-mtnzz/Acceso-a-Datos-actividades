package com.Acceso_a_Datos.a06.videojuegos.internal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Videojuego {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false, length = 50)
    private String plataforma;

    @Column(nullable = false, length = 50)
    private String genero;

    @Column(name = "fecha_lanzamiento", nullable = false)
    private LocalDate fechaLanzamiento;

    @Column(nullable = false)
    @DecimalMin(value = "0.0", message = "No te pagan por llevarte un juego")
    private Double precio;

    @Column(nullable = false)
    @DecimalMin(value = "0.0", message = "No se pueden dejar valoraciones negativas")
    @DecimalMax(value = "10.0", message = "No se pueden dejar valoraciones superiores a 10")
    private Double puntuacion;
}
