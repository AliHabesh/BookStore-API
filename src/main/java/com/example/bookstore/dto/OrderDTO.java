package com.example.bookstore.dto;

import com.example.bookstore.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private double totalPrice;
    private String email;
    private String uid;

    private List<BookDTO> books;


}
