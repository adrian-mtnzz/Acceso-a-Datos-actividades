package com.Acceso_a_Datos.a06.videojuegos.service;

import com.Acceso_a_Datos.a06.videojuegos.internal.model.Videojuego;
import com.Acceso_a_Datos.a06.videojuegos.internal.repository.VideojuegosRepository;

import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VideojuegosServiceImpl implements VideojuegosService {

    private final VideojuegosRepository repo;

    @Override
    public Videojuego save(Videojuego videojuego) {

        // Inserta un nuevo videojuego
        return repo.save(videojuego);
    }

    @Override
    public List<Videojuego> findAll() {

        // Obtiene todos los videojuegos
        return repo.findAll();
    }

    @Override
    public Optional<Videojuego> findById(Long id) {

        // Obtiene un videojuego por id
        return repo.findById(id);
    }

    @Override
    public Optional<Videojuego> update(Long id, Videojuego vnew) {

        // Actualiza un videojuego pide el id por seguridad que se obtiene de la uri de la llamada al endpoint
        // para que no se pida actualizar el juego 4 y se cambie el 10 por ejemplo
        return repo.findById(id).map(v-> {
            v.setTitulo(vnew.getTitulo());
            v.setPlataforma(vnew.getPlataforma());
            v.setGenero(vnew.getGenero());
            v.setFechaLanzamiento(vnew.getFechaLanzamiento());
            v.setPrecio(vnew.getPrecio());
            v.setPuntuacion(vnew.getPuntuacion());

            // Actualiza los campos uno a uno y despues guarda los cambios
            return repo.save(v);
        });
    }

    @Override
    public void deleteById(Long id) {

        // Comprueba si existe y despues lo elimina
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else throw new EntityNotFoundException("No se puede eliminar el videojuego porque no existe");
    }

    @Override
    public List<Videojuego> findByPlataforma(String plataforma) {

        // Lista los videojuegos filtrando por plataforma
        return repo.findByPlataforma(plataforma);
    }

    @Override
    public List<Videojuego> findByPuntuacion(Double puntuacionMinima) {

        // Lista los videojuegos filtrando por puntuacion minima
        return repo.findByPuntuacionGreaterThan(puntuacionMinima);
    }
}
