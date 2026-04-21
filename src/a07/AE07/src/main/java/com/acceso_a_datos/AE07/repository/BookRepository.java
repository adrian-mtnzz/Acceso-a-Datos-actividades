package com.acceso_a_datos.AE07.repository;

import com.acceso_a_datos.AE07.model.Book;
import com.acceso_a_datos.AE07.model.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

    Optional<Book> findByTitleIgnoringCase(String title);
    List<Book> findByAuthorIgnoringCase(String author);
    List<Book> findByGenre(Genre genre);
    List<Book> findByIsAvailable(Boolean isAvailable);
    List<Book> findByPriceGreaterThan(Double price);
    List<Book> findByPriceLessThan(Double price);
    List<Book> findByPagesGreaterThan(Integer pages);
    List<Book> findByPagesLessThan(Integer pages);
    
    List<Book> findByDeletedTrue();
    @Query(value = "{ 'deleted': true, 'deletedAt': { $lte: ?0 } }")
    List<Book> findDeletedBefore(Instant limitDate);
    
    
    Boolean existsByTitleAndAuthor(String title, String author);
    Boolean existsByIdAndDeletedTrue(String id);
}
