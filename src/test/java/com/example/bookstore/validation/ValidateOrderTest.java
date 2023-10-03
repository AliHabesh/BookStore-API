package com.example.bookstore.validation;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidateOrderTest {
    /**
     * Unit test for the Order validation class.
     */

    @Test
    void validatedOrderDataSuccess() {
        ValidateOrder validateOrder = new ValidateOrder();
        Order order = validateOrder.validateOrderData(new Order(25, "shadi97ah@gmail.com", List.of(new Book("Harry Potter", 22, 2))));
        assertTrue(order.getTotalPrice() == 25
                            && order.getEmail().equals("shadi97ah@gmail.com")
                            && order.getBooks().size() > 0);
    }

    @Test
    void invalidOrderThrowsException() {
        ValidateOrder validateOrder = new ValidateOrder();
        assertThrows(IllegalArgumentException.class, () -> {
             validateOrder.validateOrderData(new Order(25, "", List.of(new Book("Harry Potter", 22, 2))));
        });
    }


}