package com.example.bookstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;
    private String title;
    private int cost;
    private int quantity;


    public Book(String title, int cost, int quantity){
        this.title = title;
        this.cost = cost;
        this.quantity = quantity > 10 ? 10 : quantity;
    }

    public Book(String title, int cost){
        this.title = title;
        this.cost = cost;
    }


}
