package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByTitle(String title);


    List<Book> findAll();

    @Query("SELECT COUNT(o) FROM orders o JOIN o.books b WHERE b.Id = :bookId")
    int countOrdersByBookId(@Param("bookId") int bookId);

}
