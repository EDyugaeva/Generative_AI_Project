package com.epam.esm.services;

import com.epam.esm.model.Author;

import java.util.List;

public interface AuthorService {
    Author saveAuthor(Author author);
    Author updateAuthor(Author author);
    Author getAuthorById(Long id);
    List<Author> getAllAuthors();
    void deleteAuthor(Long id);
}
