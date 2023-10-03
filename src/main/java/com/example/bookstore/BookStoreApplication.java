package com.example.bookstore;

import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class BookStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(BookRepository bookRepository){
      return args -> {
          bookRepository.saveAll(generateListOfBooks());
      };


    }

    private List<Book> generateListOfBooks(){
        return new ArrayList<>(List.of(
                new Book("Fellowship of the book", 5, 10),
                new Book("Books and the chamber of books", 10, 10),
                new Book("The Return of the Book", 15, 10),
                new Book("Limited Collectors Edition", 75, 10)
        ));
    }


}
