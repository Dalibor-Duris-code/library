package com.example.library.repository;

import com.example.library.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, Integer> {
    List<Book> findByNameContainingIgnoreCase(String name);

    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByDateReleaseContainingIgnoreCase(String date);
}
