package com.epam.esm.controllers;


import com.epam.esm.model.Author;
import com.epam.esm.services.AuthorService;
import com.epam.esm.validators.AuthorValidator;
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
@Slf4j
@RequestMapping("/authors")
public class AuthorController extends BaseController{
    private final AuthorService authorService;
    private final AuthorValidator authorValidator;


    public AuthorController(AuthorService authorService, AuthorValidator authorValidator) {
        this.authorService = authorService;
        this.authorValidator = authorValidator;
    }

    @PostMapping
    @Operation(summary = "Save a new author", description = "Save a new author to the system")
    public ResponseEntity<Author> saveAuthor(@RequestBody @Valid Author author, BindingResult bindingResult) {
        log.info("Saving author: {}", author);

        authorValidator.validate(author, bindingResult);
        validate(bindingResult);

        Author savedAuthor = authorService.saveAuthor(author);
        return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an author by ID", description = "Update an author in the system by its ID")
    public ResponseEntity<Author> updateAuthor(
            @Parameter(description = "ID of the author to be updated") @PathVariable Long id,
            @RequestBody @Valid Author updatedAuthor, BindingResult bindingResult) {
        log.info("Updating author with ID {}: {}", id, updatedAuthor);

        validate(bindingResult);

        Author existingAuthor = authorService.getAuthorById(id);
        existingAuthor.setFirstName(updatedAuthor.getFirstName());
        existingAuthor.setLastName(updatedAuthor.getLastName());

        Author savedAuthor = authorService.updateAuthor(existingAuthor);
        return new ResponseEntity<>(savedAuthor, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Get an author by ID", description = "Get an author by its unique ID")
    public ResponseEntity<Author> getAuthorById(
            @Parameter(description = "ID of the author to be obtained") @PathVariable Long id) {
        log.info("Attempting to retrieve author with ID: {}", id);
        Author author = authorService.getAuthorById(id);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get all authors", description = "Get a list of all authors in the system")
    public ResponseEntity<List<Author>> getAllAuthors() {
        log.info("Retrieving all authors");
        List<Author> authors = authorService.getAllAuthors();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an author by ID", description = "Delete an author from the system by its ID")
    public ResponseEntity<Void> deleteAuthor(
            @Parameter(description = "ID of the author to be deleted") @PathVariable Long id) {
        log.info("Deleting author with ID: {}", id);
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
