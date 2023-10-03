package com.example.bookstore.controller;

import com.example.bookstore.exceptions.CustomException;
import com.example.bookstore.model.Book;
import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/books/")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }


    @Operation(summary = "Restock all the books with the default value of 10")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200",
                    description = "all the books restocked",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "500",
                    description = "Restocking was unsuccessful, most likely due to Limited edition books not available",
                    content = @Content)
    })
    @PostMapping("restock")
    public ResponseEntity<List<Book>> restockAllBooksByTen(){
        try{
            return ResponseEntity.ok(bookService.restockBooksByTen());
        }catch (CustomException e){
            throw e;
        }

    }

    @Operation(summary = "Restock a specified books with user input value, the value has to be a multiple of 10")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200",
                    description = "the book has been restocked",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "500",
                    description = "Restocking was unsuccessful",
                    content = @Content)
    })
    @PostMapping("/restock/{bookTitle}/{quantity}")
    public ResponseEntity<Book> restockBookById(@PathVariable("bookTitle") String bookTitle, @PathVariable("quantity") int quantity){
        try{
            return ResponseEntity.ok(bookService.restockBook(quantity, bookTitle));
        }catch (CustomException e){
            throw e;
        }
    }

    //For simplicity, I went with a controller-level custom exception.
    //A better solution would be to create global exception level.
    @ExceptionHandler({CustomException.class})
    public ResponseEntity<String> handleControllerLevelException(CustomException ex) {
        // Customize the response for the CustomException at the controller level
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("error: " + ex.getMessage()+"\n\nFor more information, visit our API documentation http://www.localhost:8080/swagger-ui/index.html");
    }
}
