package com.Acceso_a_Datos.a06.videojuegos.controller;

import com.Acceso_a_Datos.a06.videojuegos.internal.model.Videojuego;
import com.Acceso_a_Datos.a06.videojuegos.service.VideojuegosService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/videojuegos")
@RequiredArgsConstructor
public class VideojuegosController {

    private final VideojuegosService service;

    // Si alguno de los parametros anotados con @Valid no pasa el filtro
    // se devuelve automaticamente una respuesta Http 400 Bad Request

    // Endpoint creacion de juego
    @PostMapping
    public ResponseEntity<Videojuego> createVideojuego(@Valid @RequestBody Videojuego v) {

        // Guarda el juego
        Videojuego vSaved = service.save(v);

        // Crea la uri del nuevo recurso
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(vSaved.getId())
                .toUri();

        // Devuelve 201 y la ubicacion
        return ResponseEntity.created(location).build();
    }

    // Endpoint listar videojuegos
    @GetMapping
    public ResponseEntity<List<Videojuego>> getAllVideojuegos() {

        // Devuelve 200 y la lista de videojuegos
        return ResponseEntity.ok(service.findAll());
    }
    // Endpoint para buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<Videojuego> getVideojuego(@PathVariable Long id) {

        // Busca el juego por id si lo encuentra devuelve 200 y el videojuego
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para actualizar un videojuego
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVideojuego(@PathVariable Long id, @Valid @RequestBody Videojuego v) {

        // Como es un update devuelve body vacio y Status Code 204 NO CONTENT
        service.update(id, v);
    }

    // Endpoint para borrar un videojuego por id
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVideojuego(@PathVariable Long id) {

        // Como es un delete devuelve body vacio y Status Code 204 NO CONTENT
        service.deleteById(id);
    }

    // Endpoint para filtrar por plataforma
    @GetMapping(params = "plataforma")
    public ResponseEntity<List<Videojuego>> getVideojuegosByPlataforma(@RequestParam String plataforma) {

        // Devuelve 200 y la lista de juegos filtrada por plataforma
        return ResponseEntity.ok(service.findByPlataforma(plataforma));
    }

    // Endpoint para filtrar por puntuacion minima
    @GetMapping(params = "puntuacion")
    public ResponseEntity<List<Videojuego>> getVideojuegosByPuntacion(@RequestParam Double puntuacion) {

        // Devuelve 200 y la lista de juegos filtrada por puntuacion
        return ResponseEntity.ok(service.findByPuntuacion(puntuacion));
    }
}

