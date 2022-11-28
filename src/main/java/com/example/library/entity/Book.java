package com.example.library.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
public class Book {
    //@Id
    @MongoId
    private String bookId;
    private String name;
    private String author;
    private String genre;
    private Integer pageNumber;
    private Integer bookNumber;

}
