package com.epam.esm.services.impl;

import com.epam.esm.exception.DataNotFoundException;
import com.epam.esm.model.Book;
import com.epam.esm.model.Genre;
import com.epam.esm.repository.BookRepository;
import com.epam.esm.services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book saveBook(Book book) {
        log.info("Saving new book {}", book);
        return bookRepository.save(book);
    }

    @Override
    public Book update(Book book) {
        log.info("Updating new book {}", book);
        return bookRepository.save(book);    }

    @Override
    public Book getBookById(Long id) {
        log.info("Attempting to retrieve book with ID: {}", id);
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.orElseThrow(() -> new DataNotFoundException("Book not found with ID: " + id));
    }

    @Override
    public List<Book> getAllBooks() {
        log.info("Attempting to retrieve all books");
        return bookRepository.findAll();
    }

    @Override
    public void deleteBook(Long id) {
        log.info("Attempting to delete book with id = {}", id);
        if (!bookRepository.existsById(id)) {
            log.warn("Book not found with ID: {}", id);
            throw new DataNotFoundException("Book not found with ID: " + id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> searchByTitle(String title) {
        log.info("Attempting to retrieve books with title: {}", title);
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Book> searchByAuthor(String authorFirstName, String authorLastName) {
        log.info("Attempting to retrieve books with author name = {}, lastname = {}",
                authorFirstName, authorLastName);
        return bookRepository.findByAuthorFirstNameAndAuthorLastName(authorFirstName, authorLastName);
    }

    @Override
    public List<Book> searchByGenre(Genre genre) {
        log.info("Attempting to retrieve books with genre: {}", genre);
        return bookRepository.findByGenre(genre);
    }
}
