package com.example.library.services;

import com.example.library.entity.Book;
import com.example.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository)
    {
        this.repository = repository;
    }

    public List<Book> findAll()
    {
        return repository.findAll();
    }

    public Book add(Book book)
    {
        return this.repository.save(book);
    }

    public Book update(Book book)
    {
        return this.repository.save(book);
    }

    public void delete(Book book)
    {
        this.repository.delete(book);
    }

    public List<Book> findByNameContainingIgnoreCase(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

}
