package com.epam.esm.services.impl;

import com.epam.esm.exception.DataNotFoundException;
import com.epam.esm.model.Book;
import com.epam.esm.model.Genre;
import com.epam.esm.repository.GenreRepository;
import com.epam.esm.services.GenreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Genre saveGenre(Genre genre) {
        log.info("Saving genre: {}", genre);
        return genreRepository.save(genre);
    }

    @Override
    public Genre updateGenre(Genre genre) {
        log.info("Updating genre: {}", genre);
        return genreRepository.save(genre);
    }

    @Override
    public Genre getGenreById(Long id) {
        log.info("Attempting to retrieve genre with ID: {}", id);
        Optional<Genre> optionaloptionalGenre = genreRepository.findById(id);
        return optionaloptionalGenre.orElseThrow(() -> new DataNotFoundException("Genre not found with ID: " + id));
    }

    @Override
    public List<Genre> getAllGenres() {
        log.info("Retrieving all genres");
        return genreRepository.findAll();
    }

    @Override
    public void deleteGenre(Long id) {
        log.info("Deleting genre with ID: {}", id);
        if (!genreRepository.existsById(id)) {
            log.warn("Genre not found with ID: {}", id);
            throw new DataNotFoundException("Genre not found with ID: " + id);
        }
        genreRepository.deleteById(id);
    }
}
