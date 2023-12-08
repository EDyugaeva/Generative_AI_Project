package com.epam.esm.services.impl;

import com.epam.esm.exception.DataNotFoundException;
import com.epam.esm.model.Author;
import com.epam.esm.repository.AuthorRepository;
import com.epam.esm.services.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author saveAuthor(Author author) {
        log.info("Saving author: {}", author);
        return authorRepository.save(author);
    }

    @Override
    public Author updateAuthor(Author author) {
        log.info("Updating author: {}", author);
        return authorRepository.save(author);    }

    @Override
    public Author getAuthorById(Long id) {
        log.info("Attempting to retrieve author with ID: {}", id);
        return authorRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Author not found with ID: {}", id);
                    return new DataNotFoundException("Author not found with ID: " + id);
                });
    }

    @Override
    public List<Author> getAllAuthors() {
        log.info("Retrieving all authors");
        return authorRepository.findAll();
    }

    @Override
    public void deleteAuthor(Long id) {
        log.info("Deleting author with ID: {}", id);
        if (!authorRepository.existsById(id)) {
            log.warn("Author not found with ID: {}", id);
            throw new DataNotFoundException("Author not found with ID: " + id);
        }
        authorRepository.deleteById(id);
    }
}
