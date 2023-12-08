package com.epam.esm.controllers;

import com.epam.esm.model.Genre;
import com.epam.esm.services.GenreService;
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
@RequestMapping("/genre")
public class GenreController extends BaseController {

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    private GenreService genreService;

    @PostMapping
    @Operation(summary = "Save a new genre", description = "Save a new genre to the system")
    public ResponseEntity<Genre> saveGenre(@RequestBody @Valid Genre genre, BindingResult bindingResult) {
        log.info("Saving genre: {}", genre);
        validate(bindingResult);

        Genre savedGenre = genreService.saveGenre(genre);
        return new ResponseEntity<>(savedGenre, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an author by ID", description = "Update an author in the system by its ID")
    public ResponseEntity<Genre> updateGenre(
            @Parameter(description = "ID of the author to be updated") @PathVariable Long id,
            @RequestBody @Valid Genre updatedGenre, BindingResult bindingResult) {
        log.info("Updating genre with ID {}: {}", id, updatedGenre);

        validate(bindingResult);

        Genre existingGenre = genreService.getGenreById(id);
        existingGenre.setName(updatedGenre.getName());

        Genre savedGenre = genreService.updateGenre(existingGenre);
        return new ResponseEntity<>(savedGenre, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a genre by ID", description = "Get a genre by its unique ID")
    public ResponseEntity<Genre> getGenreById(@PathVariable Long id) {
        log.info("Attempting to retrieve genre with ID: {}", id);
        return new ResponseEntity<>(genreService.getGenreById(id), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get all genres", description = "Get a list of all genres in the system")
    public ResponseEntity<List<Genre>> getAllGenres() {
        log.info("Retrieving all genres");
        List<Genre> genres = genreService.getAllGenres();
        return new ResponseEntity<>(genres, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a genre by ID",
            description = "Delete a genre from the system by its ID")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        log.info("Deleting genre with ID: {}", id);
        genreService.deleteGenre(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
