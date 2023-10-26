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
     * @return Returns the order if it's valid. In case of invalid order, method throws CustomException.
     */
    public Order validateOrderData(Order order){
        containsDuplicate(order);
        orderExceedsCostLimit(order);
        orderWithNoEmail(order);
        return order;
    }

    /***
     * HashSet doesn't store duplicate data, therefore if Set is smaller it means the bookList had duplicate data.
     * @Performance The method has time complexity of O(N log N)
     */
    private void containsDuplicate(Order order){
        List<Book> bookList = order.getBooks();
        Set<Book> bookSet = new HashSet<>(bookList);
        if (bookSet.size() < bookList.size())
            throw new CustomException("duplicate fields is not allowed");
    }

    /**
     * Cost limit is at 120$
     * @param order
     */
    private void orderExceedsCostLimit(Order order){
        List<Book> bookList = order.getBooks();
        int totalOrderPrice = 0;
        for (Book book : bookList) {
            totalOrderPrice += book.getCost();
        }

        if (totalOrderPrice > 120)
            throw new CustomException("Order exceeds the cost limit of 120$");
    }

    private void orderWithNoEmail(Order order){
        if (order.getEmail().isEmpty())
            throw new CustomException("Order must contain email");
    }




}
