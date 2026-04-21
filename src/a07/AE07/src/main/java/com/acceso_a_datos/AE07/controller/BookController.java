package com.acceso_a_datos.AE07.controller;

import com.acceso_a_datos.AE07.model.Book;
import com.acceso_a_datos.AE07.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Validated
public class BookController {
    
    private final BookService service;
    
    // Hay definido un global exception handler para las 400 Bad Request y las 409 Conflict
    // por lo que no los comento abajo muchas veces por no repetir
    
    
    // CREATE devuelve el libro si se ha creado y codigos 201 CREATED | 409 CONFLICT
    @PostMapping
    public ResponseEntity<Book> create(@Valid @RequestBody Book book) {
        
        return service.create(book)
                .map(b -> ResponseEntity.status(HttpStatus.CREATED).body(book))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .build());
    }
    
    // FIND ALL devuelve lista de libros 200 OK (puede estar vacia)
    @GetMapping
    public ResponseEntity<List<Book>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
    
    // FIND BY ID devuelve el libro si existe y codigos 200 OK | 404 NOT FOUND
    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable @NotBlank String id) {
        
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    
    // las listas puede estar vacia porque se me va el tiempo con la practica xD se podria devolver NO CONTENT
    
    
    // SEARCH (FIND BY) devuelve lista de libros y codigos 200 OK
    @GetMapping("/search")
    public ResponseEntity<List<Book>> findBy(@RequestParam Map<String, String> filters) {
        return ResponseEntity.ok(service.findBy(filters));
    }
    
    // LESS THAN devuelve lista de libros y codigos 200 OK
    @GetMapping("/less")
    public ResponseEntity<List<Book>> findByLessThan(@RequestParam Map<String, String> filters) {
        return ResponseEntity.ok(service.findByLessThan(filters));
    }
    
    // GREATER THAN devuelve lista de libros y codigos 200 OK
    @GetMapping("/greater")
    public ResponseEntity<List<Book>> findByGreaterThan(@RequestParam Map<String, String> filters) {
        return ResponseEntity.ok(service.findByGreaterThan(filters));
    }
    
    // UPDATE (Recibe Book) devuelve el libro actualizado y codigos 200 OK | 404 NOT FOUND
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable @NotBlank String id,
                                           @Valid @RequestBody Book book) {
        
        return service.updateBook(id, book)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // UPDATE PARCIAL (Recibe solo los campos y valores por JSON) devuelve el libro y codigos 200 OK | 404 NOT FOUND
    @PatchMapping("/{id}")
    public ResponseEntity<Book> updateOnly(@PathVariable @NotBlank String id,
                                           @RequestBody Map<String, Object> filters) {
        
        return service.updateOnly(id, filters)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // SOFT DELETE Devuelve el libro eliminado y codigos 200 OK y 404 NOT FOUND
    @DeleteMapping("/{id}")
    public ResponseEntity<Book> delete(@PathVariable @NotBlank String id) {
        
        return service.setStatusDeleted(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // FIND DELETED lo mismo de las listas codigo 200 OK
    @GetMapping("/deleted")
    public ResponseEntity<List<Book>> findDeleted() {
        return ResponseEntity.ok(service.findByDeletedTrue());
    }
    
    // Opcional manual del CRON para el delete definitivo, boolean y codigo 200 OK
    @PostMapping("/cron/delete")
    public ResponseEntity<Boolean> runCron() {
        return ResponseEntity.ok(service.checkDeletedDateCron());
    }
    
    // EXISTS boolean y codigo 200 OK
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable @NotBlank String id) {
        return ResponseEntity.ok(service.existsById(id));
    }
}
