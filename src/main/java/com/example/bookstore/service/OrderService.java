package com.example.bookstore.service;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.dto.OrderDTO;
import com.example.bookstore.exceptions.CustomException;
import com.example.bookstore.model.Book;
import com.example.bookstore.validation.ValidateOrder;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.model.Order;
import com.example.bookstore.repository.OrderRepository;
import com.example.bookstore.uid.UIDGenerator;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, BookRepository bookRepository){
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
    }

    public Optional<OrderDTO> getOrder(String email, String uid){
       Optional<Order> order = orderRepository.findOrderByEmailAndUid(email, uid);
       if (!order.isPresent())
           throw new CustomException("Based on the inputs given, no data was found.");

        Order order1 = order.get();
        OrderDTO orderDTO = new OrderDTO(order1.getTotalPrice(), order1.getEmail(), order1.getUid(), convertBookToBookDTO(order1.getBooks()));
        return Optional.of(orderDTO);
    }

    @Transactional(rollbackOn = CustomException.class)
    public Optional<OrderDTO> addOrder(OrderDTO orderDTO) {
        Order order = convertOrderDTOToOrder(orderDTO);
        if (order.getBooks().isEmpty()) {
            throw new CustomException("Order must contain at least one book. Order has been cancelled.");
        }

        for (Book book : order.getBooks()) {
            if (!isBookAvailable(book.getTitle())) {
                throw new CustomException("Book not available: " + book.getTitle()+". Order has been cancelled!");
            }
        }

        ValidateOrder validateOrder = new ValidateOrder();
        //throws exception (not a good solution, custom exception handlers would be better for the api user)
        Order orderValidated = validateOrder.validateOrderData(order);
        orderValidated.setUid(UIDGenerator.generateUID());

        Order savedOrder = orderRepository.save(orderValidated);
        reduceBookQuantity(order.getBooks(), orderDTO);
        OrderDTO orderDTO1 = new OrderDTO(order.getTotalPrice(), order.getEmail(), order.getUid(), convertBookToBookDTO(order.getBooks()));

        return Optional.of(orderDTO1);
    }

    private void reduceBookQuantity(List<Book> books, OrderDTO orderDTO) {
        List<Book> updatedBooks = new ArrayList<>();
        for (Book book : books) {
            bookRepository.findByTitle(book.getTitle()).ifPresent(currentBook -> {
                if (currentBook.getQuantity() > 0) {
                    currentBook.setQuantity(currentBook.getQuantity() - orderDTO.getBooks().stream().
                            filter(bookDTO -> bookDTO.getTitle().equals(currentBook.getTitle())).findFirst().get().getQuantity());
                    updatedBooks.add(currentBook);
                }
            });
        }
        bookRepository.saveAll(updatedBooks);
    }

    private boolean isBookAvailable(String title){
        Optional<Book> book = bookRepository.findByTitle(title);
        return book.isPresent() && book.get().getQuantity() > 0;
    }


    /**
     * @param orderDTO
     * @return Order
     */
    //Could have used other means, such as mapstruct, also should be in another custom class specifically for mapping DTO -> Obj.
    // For simplicity, this should be enough.
    private Order convertOrderDTOToOrder(OrderDTO orderDTO){
        Order order = new Order();
        int cost = 0;
        List<Book> books = new ArrayList<>();
        order.setEmail(orderDTO.getEmail());
        if (!orderDTO.getBooks().isEmpty()){
            for (BookDTO book:orderDTO.getBooks()) {
                Optional<Book> currentBook =  bookRepository.findByTitle(book.getTitle());
                if (currentBook.isPresent()){
                    books.add(currentBook.get());
                    System.out.println(currentBook.get().getCost() * book.getQuantity());
                    cost +=  currentBook.get().getCost() * book.getQuantity();
                }

            }
        }
        order.setBooks(books);
        System.out.println("Here is the cost: "+cost);
        if (cost>120)
            throw new CustomException("Price exceeds limit");

        order.setTotalPrice(cost);
        System.out.println(order);
        return order;
    }

    public List<BookDTO> convertBookToBookDTO(List<Book> books){
        List<BookDTO> bookDTOS = new ArrayList<>();
        books.forEach(book -> {
            bookDTOS.add(new BookDTO(book.getTitle(), book.getCost(), book.getQuantity()));
        });

        return bookDTOS;
    }
}
