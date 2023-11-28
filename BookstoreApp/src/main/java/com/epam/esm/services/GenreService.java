package com.epam.esm.services;

import com.epam.esm.model.Genre;

import java.util.List;

public interface GenreService {
    Genre saveGenre(Genre genre);
    Genre updateGenre(Genre genre);


    Genre getGenreById(Long id);

    List<Genre> getAllGenres();

    void deleteGenre(Long id);
}
