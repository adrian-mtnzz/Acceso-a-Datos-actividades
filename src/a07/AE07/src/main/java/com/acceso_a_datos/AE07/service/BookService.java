package com.acceso_a_datos.AE07.service;

import com.acceso_a_datos.AE07.model.Book;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BookService {
    
    // Creacion
    public Optional<Book> create(Book book);

    // Consultar
    public List<Book> findAll();
    public Optional<Book> findById(String id);
    public List<Book> findBy(Map<String, ?> filters);
    public List<Book> findByLessThan(Map<String, ?> filters);
    public List<Book> findByGreaterThan(Map<String, ?> filters);
    public List<Book> findByDeletedTrue();
    
    // Actualizar
    public Optional<Book> updateBook(String id, Book book);
    public Optional<Book> updateOnly(String id, Map<String, ?> filters);
    
    // Eliminar
    public Optional<Book> setStatusDeleted(String id);
    public boolean checkDeletedDateCron();
    
    // Checks
    public boolean existsById(String id);
    public boolean existsByTitleAndAuthor(String title, String author);
}
