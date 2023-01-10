package com.example.library.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;

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
    private LocalDate dateRelease;
    private Integer pageNumber;
    //@Lob
    private String image;

}
