package com.example.bookstore.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity(name = "orders")
@Data
@ToString
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;
    private double totalPrice;
    private String email;
    private String uid;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "order_book",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books;

    public Order(double totalPrice, String email, List<Book> books){
        this.totalPrice = totalPrice;
        this.email = email;
        this.books = books;

    }

    public Order(double totalPrice, String email, String uid, List<Book> books){
        this.totalPrice = totalPrice;
        this.email = email;
        this.books = books;
        this.uid = uid;

    }

}
