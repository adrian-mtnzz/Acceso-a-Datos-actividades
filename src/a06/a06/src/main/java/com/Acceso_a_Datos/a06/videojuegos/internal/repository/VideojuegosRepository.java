package com.Acceso_a_Datos.a06.videojuegos.internal.repository;

import com.Acceso_a_Datos.a06.videojuegos.internal.model.Videojuego;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideojuegosRepository extends JpaRepository<Videojuego, Long> {
    List<Videojuego> findByPlataforma(String plataforma);
    List<Videojuego> findByPuntuacionGreaterThan(Double puntuacionMinima);
}
