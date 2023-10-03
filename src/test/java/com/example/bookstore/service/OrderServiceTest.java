package com.example.bookstore.service;

import com.example.bookstore.dto.OrderDTO;
import com.example.bookstore.model.Order;
import com.example.bookstore.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderServiceTest {
    /**
     * Simple Unit tests, in this case integration test for both Service classes would have been a good choice to test the complete workflow.
     */
    @Mock
    private OrderRepository orderRepository;

    private OrderService orderService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        orderService = new OrderService(orderRepository,null);
    }
    @Test
    void getOrder() {
        String email = "test@example.com";
        String uid = "test-uid";
        Order mockedOrder = new Order(); // Create a mocked Order instance
        when(orderRepository.findOrderByEmailAndUid(email, uid)).thenReturn(Optional.of(mockedOrder));

        Optional<OrderDTO> result = orderService.getOrder(email, uid);
        verify(orderRepository).findOrderByEmailAndUid(email, uid);
        assertEquals(Optional.of(mockedOrder), result);
    }


}