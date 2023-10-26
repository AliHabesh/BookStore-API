package com.example.bookstore.controller;

import com.example.bookstore.exceptions.CustomException;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Order;
import com.example.bookstore.dto.OrderDTO;
import com.example.bookstore.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @Operation(summary = "Finds a specified order based on email and unique id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200",
                    description = "Order was found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDTO.class)) }),
            @ApiResponse(responseCode = "500",
                    description = "Order was not found or input invalid",
                    content = @Content)
    })
    @GetMapping("/{email}/{orderUID}")
    public ResponseEntity<OrderDTO> getCustomerOrder(@PathVariable("email") String email, @PathVariable("orderUID") String uid) {
        Optional<OrderDTO> order = orderService.getOrder(email, uid);
        return ResponseEntity.ok(order.get());
    }

    @Operation(summary = "Creates order")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200",
                    description = "Order was created!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDTO.class)) }),
            @ApiResponse(responseCode = "500",
                    description = "Order was not created",
                    content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO){
        Optional<OrderDTO> order = orderService.addOrder(orderDTO);
        return ResponseEntity.ok(order.get());
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
