package com.epam.esm.controllers;

import com.epam.esm.model.Book;
import com.epam.esm.model.Genre;
import com.epam.esm.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/books")
@Slf4j
public class BookController extends BaseController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @Operation(summary = "Save a new book", description = "Save a new book to the system")
    public ResponseEntity<Book> saveBook(@RequestBody @Valid Book book, BindingResult bindingResult) {
        log.info("Saving book: {}", book);

        validate(bindingResult);

        Book savedBook = bookService.saveBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a book by ID", description = "Update a book in the system by its ID")
    public ResponseEntity<Book> updateBook(
            @Parameter(description = "ID of the book to be updated") @PathVariable Long id,
            @RequestBody @Valid Book updatedBook, BindingResult bindingResult) {
        log.info("Updating book with ID {}: {}", id, updatedBook);

        validate(bindingResult);

        Book existingBook = bookService.getBookById(id);
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setGenre(updatedBook.getGenre());
        existingBook.setPrice(updatedBook.getPrice());
        existingBook.setQuantityAvailable(updatedBook.getQuantityAvailable());

        return new ResponseEntity<>(bookService.update(existingBook), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a book by ID", description = "Get a book by its unique ID")
    public ResponseEntity<Book> getBookById(
            @Parameter(description = "ID of the book to be obtained") @PathVariable Long id) {
        log.info("Attempting to retrieve book with ID: {}", id);
        Book book = bookService.getBookById(id);
        return (book != null) ? new ResponseEntity<>(book, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    @Operation(summary = "Get all books", description = "Get a list of all books in the system")
    public ResponseEntity<List<Book>> getAllBooks() {
        log.info("Retrieving all books");
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book by ID", description = "Delete a book from the system by its ID")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "ID of the book to be deleted") @PathVariable Long id) {
        log.info("Deleting book with ID: {}", id);
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/searchByTitle")
    @Operation(summary = "Search books by title", description = "Search books by title in the system")
    public ResponseEntity<List<Book>> searchByTitle(
            @Parameter(description = "Title of the book to search for") @RequestParam String title) {
        log.info("Searching books by title: {}", title);
        List<Book> books = bookService.searchByTitle(title);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/searchByAuthor")
    @Operation(summary = "Search books by author", description = "Search books by author in the system")
    public ResponseEntity<List<Book>> searchByAuthor(
            @Parameter(description = "First name of the author") @RequestParam String authorFirstName,
            @Parameter(description = "Last name of the author") @RequestParam String authorLastName) {
        log.info("Searching books by author: {} {}", authorFirstName, authorLastName);
        List<Book> books = bookService.searchByAuthor(authorFirstName, authorLastName);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/searchByGenre")
    @Operation(summary = "Search books by genre", description = "Search books by genre in the system")
    public ResponseEntity<List<Book>> searchByGenre(
            @Parameter(description = "Genre of the book") @RequestParam String genre) {
        log.info("Searching books by genre: {}", genre);
        List<Book> books = bookService.searchByGenre(genre);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}
