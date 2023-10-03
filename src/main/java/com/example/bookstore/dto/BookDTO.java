package com.example.bookstore.dto;

import com.example.bookstore.model.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private String title;


    private double bookPrice;

    private int quantity;

    public BookDTO(String title, double bookPrice){
        this.title = title;
        this.bookPrice = bookPrice;
    }


}


