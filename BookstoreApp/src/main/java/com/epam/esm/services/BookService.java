package com.epam.esm.services;

import com.epam.esm.model.Book;
import com.epam.esm.model.Genre;

import java.util.List;

public interface BookService {
    Book saveBook(Book book);
    Book update(Book book);

    Book getBookById(Long id);

    List<Book> getAllBooks();

    void deleteBook(Long id);
    List<Book> searchByTitle(String title);

    List<Book> searchByAuthor(String authorFirstName, String authorLastName);

    List<Book> searchByGenre(String genre);
}
