package com.Acceso_a_Datos.a06.videojuegos.config;

import com.Acceso_a_Datos.a06.videojuegos.internal.model.Videojuego;
import com.Acceso_a_Datos.a06.videojuegos.service.VideojuegosService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
public class VideojuegosDataLoader {

    private final VideojuegosService service;

    @Bean
    CommandLineRunner initData() {
        return args -> {
            // Juego 1
            service.save(new Videojuego(null, "The Witcher 3", "PC", "RPG",
                    LocalDate.of(2015, 5, 19), 39.99, 9.8));

            // Juego 2
            service.save(new Videojuego(null, "Elden Ring", "PS5", "RPG",
                    LocalDate.of(2022, 2, 25), 69.99, 9.6));

            // Juego 3
            service.save(new Videojuego(null, "FIFA 23", "PS5", "Deportes",
                    LocalDate.of(2022, 9, 30), 69.99, 3.2));

            // Juego 4
            service.save(new Videojuego(null, "Halo Infinite", "Xbox Series X", "Shooter",
                    LocalDate.of(2021, 12, 8), 59.99, 6.5));

        };
    }
}