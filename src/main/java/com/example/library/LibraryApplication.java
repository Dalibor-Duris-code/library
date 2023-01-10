package com.example.library;

import com.example.library.entity.Book;
import com.example.library.repository.BookRepository;
import com.vaadin.exampledata.ChanceIntegerType;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StopWatch;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@Log4j2
public class LibraryApplication{

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    @Bean
    public CommandLineRunner createDemoDataIfNeeded(BookRepository repository)
    {
        return args -> {
            if(repository.count() == 0)
            {
                log.info("Generating demo data..");
                var generator = new ExampleDataGenerator<>(Book.class, LocalDateTime.now());
                generator.setData(Book::setImage, DataType.BOOK_IMAGE_URL);
                generator.setData(Book::setName, DataType.BOOK_TITLE);
                generator.setData(Book::setAuthor, DataType.FULL_NAME);
                generator.setData(Book::setPageNumber, new ChanceIntegerType("integer", "{min: 1, max: 1000}"));
                generator.setData(Book::setDateRelease, DataType.DATE_LAST_10_YEARS);

                var stopWatch = new StopWatch();
                stopWatch.start();

                List<Book> books = generator.create(100, new Random().nextInt());
                repository.saveAll(books);

                stopWatch.stop();
                log.info("Demo data generated in" + stopWatch.getTotalTimeSeconds() + "sec.");
            }
        };
    }
}
