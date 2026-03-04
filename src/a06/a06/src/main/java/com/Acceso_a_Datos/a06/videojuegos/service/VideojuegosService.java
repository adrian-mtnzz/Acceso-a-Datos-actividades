package com.Acceso_a_Datos.a06.videojuegos.service;

import com.Acceso_a_Datos.a06.videojuegos.internal.model.Videojuego;

import java.util.List;
import java.util.Optional;

public interface VideojuegosService {
    // Crear
    Videojuego save(Videojuego videojuego);
    // Listar todos
    List<Videojuego> findAll();
    // Buscar por id
    Optional<Videojuego> findById(Long id);
    // Actualizar
    Optional<Videojuego> update(Long id, Videojuego videojuego);
    // Eliminar
    void deleteById(Long id);
    // Filtrar por plataforma
    List<Videojuego> findByPlataforma(String plataforma);
    // Filtrar por puntuacion
    List<Videojuego> findByPuntuacion(Double puntuacionMinima);
}
