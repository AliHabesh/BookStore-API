# Bookstore REST API

## Overview

This README provides documentation for the Bookstore REST API, which allows customers to order fresh books directly from the bookstore and enables an admin (credentials provided) to re-stock books. The system is implemented in Java and responds with JSON. The bookstore offers a selection of four different books, each with its title and pricing:

- **Book A**: Fellowship of the Book - $5
- **Book B**: Books and the Chamber of Books - $10
- **Book C**: The Return of the Book - $15
- **Book D**: Limited Collector's Edition - $75

Customers can view available books and place orders, while the admin can manage stock levels. Please read this document to understand how to use the API and be aware of its limitations.

## API Endpoints

### Base URL

The base URL for all API endpoints is: `http://example.com/bookstore/api`

### Authentication

#### Customer Authentication

- Customers need to provide their credentials (username and password) for authentication.
- Include the following in the request header:
  - Username: `YourUsername`
  - Password: `YourPassword`

#### Admin Authentication

- The admin uses specific credentials for authentication.
- Include the following in the request header:
  - Username: `Uncle_Bob_1337`
  - Password: `TomCruiseIsUnder170cm`

### Available Endpoints

1. **GET /books**
   - Retrieve the list of available books with titles and prices.

2. **POST /orders**
   - Place an order for books. Requires customer authentication.
   - Request body should be in JSON format and specify the books to order.

3. **GET /orders**
   - Retrieve a list of orders placed by the customer. Requires customer authentication.

4. **POST /restock**
   - Re-stock books. Requires admin authentication.
   - Admin should specify the quantity to be restocked for each book.

## Book Inventory

The bookstore offers the following books with titles and prices:

- Book A: Fellowship of the Book - $5
- Book B: Books and the Chamber of Books - $10
- Book C: The Return of the Book - $15
- Book D: Limited Collector's Edition - $75

### Restocking Books

- Book A, Book B, and Book C can be restocked in multiples of 10.
- Use the `/restock` endpoint with admin authentication to re-stock books.
- Admin should specify the quantity to be restocked for each book.

## Ordering Books

Customers can place orders for books using the `/orders` endpoint. The request should be in JSON format and include the books they want to order. Here's an example request body:

```json
{
  "books": [
    {
      "title": "Book A",
      "quantity": 3
    },
    {
      "title": "Book C",
      "quantity": 2
    }
  ]
}
