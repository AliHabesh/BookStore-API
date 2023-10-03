package com.example.bookstore.service;

import com.example.bookstore.exceptions.CustomException;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final String limitedEdition = "Limited Collectors Edition";
    @Autowired
    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }


    /**
     * Restocks all the books with the default value of +10
     * Throws exception if a book is not available to restock
     */
    public List<Book> restockBooksByTen(){
        int defaultValue = 10;
        List<Book> bookList = bookRepository.findAll();

        bookList.forEach(book -> {
            if (!limitedEdition.equals(book.getTitle())) {
                book.setQuantity(book.getQuantity() + defaultValue);
            } else if (limitedEditionBook(limitedEdition) > 0) {
                throw new CustomException("Book is of limited edition, no more are available");
            } else {
                book.setQuantity(book.getQuantity() + defaultValue);
            }
        });

       return bookRepository.saveAll(bookList);
    }

    /**
     *
     * @param quantity  amount of books wanted (multiples of 10)
     * @param bookTitle Title of the book
     * @return the restocked book with updated data.
     */
    public Book restockBook(int quantity, String bookTitle){
        if (!(quantity % 10 == 0))
            throw new CustomException("Quantity must be a multiple of 10.");

        if (bookTitle.equals(limitedEdition) && limitedEditionBook(bookTitle) > 9)
            throw new CustomException("Book is of limited edition, no more are available.");


        Optional<Book> book = bookRepository.findByTitle(bookTitle);
        if (book.isPresent())
            book.get().setQuantity(book.get().getQuantity()+quantity);

        return bookRepository.save(book.get());
    }

    /**
     * @param title of the book
     * @return returns the total book quantity from both orders and books repositories.
     */
    public int limitedEditionBook(String title){
        int LimitedEditionQuantity = 0;
        Optional<Book> limitedBook = bookRepository.findByTitle(title);

        if (!limitedBook.isPresent())
            return -1;

        Book book = limitedBook.get();
        return book.getQuantity() + bookRepository.countOrdersByBookId(book.getId());
    }


}
