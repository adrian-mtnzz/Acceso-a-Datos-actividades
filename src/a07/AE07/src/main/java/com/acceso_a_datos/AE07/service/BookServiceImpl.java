package com.acceso_a_datos.AE07.service;

import com.acceso_a_datos.AE07.model.Book;
import com.acceso_a_datos.AE07.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    
    // Inyectados por constructor (RequiredArgsConstructor)
    private final BookRepository repo;
    private final MongoTemplate mongoTemplate;
    
    @Override
    public Optional<Book> create(Book book) {
        
        // Verificar que no haya otro libro con ese titulo y autor
        if (existsByTitleAndAuthor(book.getTitle(), book.getAuthor())) return Optional.empty();
        
        book.setDeleted(false);
        book.setDeletedAt(null);
        
        return Optional.of(repo.save(book));
    }
    
    @Override
    public List<Book> findAll() {
        
        // Filtrado para que no muestre los registros que tienen un borrado logico
        return repo.findAll().stream()
                .filter(this::isNotDeleted)
                .toList();
    }
    
    @Override
    public Optional<Book> findById(String id) {
        
        // Filtrado para que no muestre los registros que tienen un borrado logico
        return repo.findById(id).filter(this::isNotDeleted);
    }
    
    @Override
    public List<Book> findBy(Map<String, ?> filters) {
        
        // Crear query para la consulta
        Query query = new Query();
        
        // Recorrer todos los filtros recibidos
        filters.forEach((key, value) -> {
            
            // Early return de nulls
            if (value == null) return;
            
            // Titulo y autor se buscan por LIKE (ignore case)
            if ("title".equals(key) || "author".equals(key)) {
                
                query.addCriteria(
                        Criteria.where(key).regex(value.toString(), "i")
                );
                return;
            }
            
            
            // Campos numericos hay que castearlos
            
            // Precio es Double
            if ("price".equals(key)) {
                
                Object parsedValue = value;
                
                if (value instanceof String s) {
                    
                    try {
                        parsedValue = Double.parseDouble(s);
                        
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Valor numerico invalido para price");
                    }
                }
                
                // Aplica filtro EQ
                query.addCriteria(Criteria.where(key).is(parsedValue));
                return;
            }
            
            // Pages es Integer
            if ("pages".equals(key)) {
                
                Object parsedValue = value;
                
                if (value instanceof String s) {
                    
                    try {
                        parsedValue = Integer.parseInt(s);
                        
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Valor numerico invalido para pages");
                    }
                }
                
                // Aplica filtro EQ
                query.addCriteria(Criteria.where(key).is(parsedValue));
                return;
            }
            
            // Resto campos tambien con filtro EQ
            if ("genre".equals(key) || "isAvailable".equals(key)) {
                query.addCriteria(Criteria.where(key).is(value));
                return;
            }
            
            // Campos no permitidos
            throw new IllegalArgumentException("Campo no permitido para filtro: " + key);
            
        });
        
        // Ejecutar la query personalizada y exluir eliminados
        return mongoTemplate.find(query, Book.class).stream()
                .filter(this::isNotDeleted)
                .toList();
    }
    
    @Override
    public List<Book> findByLessThan(Map<String, ?> filters) {
        
        // Crear query para la consulta
        Query query = new Query();
        
        // Recorrer los filtros y añadir criterios dinámicamente a la query
        filters.forEach((key, value) -> {
            
            // Early return de nulls
            if (value == null) return;
            
            Object parsedValue = value;
            
            // Del controller vienen como String, hay que hacer casteo
            switch (key) {
                
                // Utiliza solo los campos numericos
                
                // Precio casteo a Integer
                case "price" -> {
                    if (value instanceof String s) {
                        
                        try {
                            parsedValue = Double.parseDouble(s);
                            
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Valor numerico invalido para price");
                        }
                    }
                }
                
                // Pages casteo a Double
                case "pages" -> {
                    if (value instanceof String s) {
                        
                        try {
                            parsedValue = Integer.parseInt(s);
                            
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Valor numerico invalido para pages");
                        }
                    }
                }
                
                default -> {
                    // Los campos no numericos no pueden usar LT
                    throw new IllegalArgumentException("Campo no permitido para LT: " + key);
                }
            }
            // Aplica el filtro LT
            query.addCriteria(Criteria.where(key).lt(parsedValue));
        });
        
        // Ejecutar la query personalizada y exluir los eliminados
        return mongoTemplate.find(query, Book.class).stream()
                .filter(this::isNotDeleted)
                .toList();
    }
    
    @Override
    public List<Book> findByGreaterThan(Map<String, ?> filters) {
        
        // Crear query para la consulta
        Query query = new Query();
        
        // Recorrer los filtros y añadir criterios dinámicamente a la query
        filters.forEach((key, value) -> {
            
            // Early return de nulls
            if (value == null) return;
            
            Object parsedValue = value;
            
            // Del controller vienen como String, hay que hacer casteo
            switch (key) {
                
                // Utiliza solo los campos numericos
                
                // Precio casteo a Integer
                case "price" -> {
                    if (value instanceof String s) {
                        
                        try {
                            parsedValue = Double.parseDouble(s);
                        
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Valor numerico invalido para price");
                        }
                    }
                }
                
                // Pages casteo a Double
                case "pages" -> {
                    if (value instanceof String s) {
                        
                        try {
                            parsedValue = Integer.parseInt(s);
                        
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Valor numerico invalido para pages");
                        }
                    }
                }
                
                default -> {
                    // otros campos no numericos no deberian usar GT
                    throw new IllegalArgumentException("Campo no permitido para GT: " + key);
                }
            }
            // Aplica el filtro GT
            query.addCriteria(Criteria.where(key).gt(parsedValue));
        });
        
        // Ejecutar la query personalizada y exluir los eliminados
        return mongoTemplate.find(query, Book.class).stream()
                .filter(this::isNotDeleted)
                .toList();
    }
    
    @Override
    public List<Book> findByDeletedTrue() {
        return repo.findByDeletedTrue();
    }
    
    @Override
    public Optional<Book> updateBook(String id, Book book) {
        
        // Actualizar campos no nulos si existe el libro, si no devuelve un empty Optional
        return repo.findById(id).filter(this::isNotDeleted).map(existing -> {
            
            // Validar valores los valores nuevos y comprobar si son distintos a los anteriores
            String newTitle = (book.getTitle() != null && !book.getTitle().isBlank())
                    ? book.getTitle()
                    : existing.getTitle();
            
            String newAuthor = (book.getAuthor() != null && !book.getAuthor().isBlank())
                    ? book.getAuthor()
                    : existing.getAuthor();
            
            // Si son validos y distintos a los anteriores pero ya existe el libro y autor no los actualiza
            if (!newTitle.equals(existing.getTitle()) || !newAuthor.equals(existing.getAuthor())) {
                
                if (existsByTitleAndAuthor(newTitle, newAuthor)) {
                    throw new IllegalArgumentException("Ya existe un libro con ese título y autor");
                }
                
                existing.setTitle(newTitle);
                existing.setAuthor(newAuthor);
            }
            
            // Validar y actualizar resto de valores
            if (book.getGenre() != null) existing.setGenre(book.getGenre());
            if (book.getPrice() != null && book.getPrice() > 0) existing.setPrice(book.getPrice());
            if (book.getIsAvailable() != null) existing.setIsAvailable(book.getIsAvailable());
            if (book.getPages() != null && book.getPages() > 1) existing.setPages(book.getPages());
            
            // Los de delete no se permite modificarlos desde aqui
            
            return repo.save(existing);
        });
    }
    
    // Este metodo en verdad es casi como el anterior, pero lo pongo por variar un poco
    // ahora en vez de recibir el objeto Book recibe directamente un mapa clave valor de los campos a actualizar
    
    @Override
    public Optional<Book> updateOnly(String id, Map<String, ?> filters) {
        
        // flatMap + ofNullable para evitar Optional<Optional<Book>>
        return repo.findById(id).filter(this::isNotDeleted).flatMap(existing -> {
                    
                    Update update = new Update();
                    
                    // Comprobar combinacion  campos titulo y autor no repetida
                    String newTitle = existing.getTitle();
                    String newAuthor = existing.getAuthor();
                    
                    Object titleObj = filters.get("title");
                    Object authorObj = filters.get("author");
                    
                    // Comprobar el casteo por si no viene String o no es valido
                    // Si es null instanceof devuelve false y se omite el campo
                    if (titleObj instanceof String t && !t.isBlank()) {
                        newTitle = t;
                    }
                    
                    // Comprobar el casteo por si no viene String o no es valido
                    // Si es null instanceof devuelve false y se omite el campo
                    if (authorObj instanceof String a && !a.isBlank()) {
                        newAuthor = a;
                    }
                    
                    // Si son distintos a los existentes realizar la comprobacion
                    if (!newTitle.equals(existing.getTitle()) || !newAuthor.equals(existing.getAuthor())) {
                        
                        if (existsByTitleAndAuthor(newTitle, newAuthor)) {
                            throw new IllegalArgumentException("Ya existe un libro con ese titulo y autor");
                        }
                        
                        update.set("title", newTitle);
                        update.set("author", newAuthor);
                    }
            
                    // Comprobar casteos validar logica de negocio y actualizar valores que pasen el filtro
            
                    Object genre = filters.get("genre");
                    if (genre != null) {
                        update.set("genre", genre);
                    }
                    
                    Object priceObj = filters.get("price");
                    if (priceObj instanceof Number n && n.doubleValue() > 0) {
                        update.set("price", n.doubleValue());
                    }
                    
                    Object availableObj = filters.get("isAvailable");
                    if (availableObj instanceof Boolean b) {
                        update.set("isAvailable", b);
                    }
                    
                    Object pagesObj = filters.get("pages");
                    if (pagesObj instanceof Number n && n.intValue() > 1) {
                        update.set("pages", n.intValue());
                    }
                    
                    // Los campos del delete son protegidos, no se pueden actualizar fuera de sus metodos propios
                    if (filters.containsKey("deleted") ||
                            filters.containsKey("deletedAt") ||
                            filters.containsKey("id")) {
                        throw new IllegalArgumentException("No se permite modificar campos protegidos");
                    }
            
                    // Ejecutar la query de modificacion
                    Book updated = mongoTemplate.findAndModify(
                            Query.query(Criteria.where("id").is(id)),
                            update,
                            FindAndModifyOptions.options().returnNew(true),
                            Book.class
                    );
                    
                    return Optional.ofNullable(updated);
                });
    }
    
    @Override
    public Optional<Book> setStatusDeleted(String id) {
        
        // Obtiene el libro
        return repo.findById(id).map(book -> {
            
            // Lo marca como eliminado
            book.setDeleted(true);
            book.setDeletedAt(Instant.now());
            
            // Devuelve el optional del libro
            return repo.save(book);
        });
    }
    
    
    @Override
    public boolean checkDeletedDateCron() {
        
        // Pongo 90 dias como tiempo previo a la eliminacion, podria ser cualquier otro valor
        Instant limit = Instant.now().minus(90, ChronoUnit.DAYS);
        
        // Obtener los libros marcados como borrados previos al limite de tiempo
        List<Book> toDelete = repo.findDeletedBefore(limit);
        
        // Si no encuentra ninguno devuelve false
        if (toDelete.isEmpty()) return false;
        
        // Extraer los IDs
        List<String> ids = toDelete.stream()
                .map(Book::getId)
                .toList();
        
        // Eliminarlos definitivamente
        repo.deleteAllById(ids);
        return true;
    }
    
    // Metodos Check
    @Override
    public boolean existsById(String id) {
        return repo.findById(id)
                .map(this::isNotDeleted)
                .orElse(false);
    }
    
    @Override
    public boolean existsByTitleAndAuthor(String title, String author) {
        return repo.existsByTitleAndAuthor(title, author);
    }
    
    private boolean isNotDeleted(Book book) {
        return !Boolean.TRUE.equals(book.getDeleted());
    }
}
