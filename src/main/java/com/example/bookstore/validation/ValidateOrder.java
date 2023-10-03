package com.example.bookstore.validation;

import com.example.bookstore.exceptions.CustomException;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class ValidateOrder {

    /**
     * @param order
     * @return Returns the order if it's valid. In case of invalid order, method throws IllegalArgumentException.
     */
    public Order validateOrderData(Order order){
        if (containsDuplicate(order))
            throw new CustomException("duplicate fields is not allowed");

        if (orderExceedsCostLimit(order))
            throw new CustomException("Order exceeds the cost limit of 120$");

        if (orderWithNoEmail(order))
            throw new CustomException("Order must contain email");

        return order;
    }

    /***
     * @return True if list contains duplicate, false otherwise.
     * @Performance The method has time complexity of O(N log N)
     */
    private boolean containsDuplicate(Order order){
        List<Book> bookList = order.getBooks();
        Set<Book> bookSet = new HashSet<>();
        for (Book book : bookList) {
            bookSet.add(book);
        }
        return bookSet.size() < bookList.size(); //If bookSet has a smaller size than the list, then there must have been duplicate data.
    }

    /**
     * @param order
     * @return true if order exceeds cost limit of 120$.
     */
    private boolean orderExceedsCostLimit(Order order){
        List<Book> bookList = order.getBooks();
        int totalOrderPrice = 0;
        for (Book book : bookList) {
            totalOrderPrice += book.getCost();
        }
        return totalOrderPrice > 120;
    }

    private boolean orderWithNoEmail(Order order){
        return order.getEmail().isEmpty();
    }




}
