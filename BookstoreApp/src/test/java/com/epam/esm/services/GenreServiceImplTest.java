package com.epam.esm.services;
import com.epam.esm.exception.DataNotFoundException;
import com.epam.esm.model.Genre;
import com.epam.esm.repository.GenreRepository;
import com.epam.esm.services.impl.GenreServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.constants.GenreTestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class GenreServiceImplTest {
    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreServiceImpl genreService;

    @Test
    void saveGenre_Genre_whenCorrect() {
        when(genreRepository.save(TEST_GENRE)).thenReturn(TEST_GENRE);

        Genre savedGenre = genreService.saveGenre(TEST_GENRE);

        assertNotNull(savedGenre);
        assertEquals(TEST_GENRE.getId(), savedGenre.getId());
        assertEquals(TEST_GENRE.getName(), savedGenre.getName());

        verify(genreRepository, times(1)).save(TEST_GENRE);
    }

    @Test
    void updateGenre_Genre_whenCorrect() {
        when(genreRepository.save(TEST_GENRE)).thenReturn(TEST_GENRE);

        Genre updatedGenre = genreService.updateGenre(TEST_GENRE);

        assertNotNull(updatedGenre);
        assertEquals(TEST_GENRE.getId(), updatedGenre.getId());
        assertEquals(TEST_GENRE.getName(), updatedGenre.getName());

        verify(genreRepository, times(1)).save(TEST_GENRE);
    }

    @Test
    void getGenreById_Genre_whenCorrectId() {
        when(genreRepository.findById(TEST_GENRE_ID)).thenReturn(Optional.of(TEST_GENRE));

        Genre foundGenre = genreService.getGenreById(TEST_GENRE_ID);

        assertNotNull(foundGenre);
        assertEquals(TEST_GENRE.getId(), foundGenre.getId());
        assertEquals(TEST_GENRE.getName(), foundGenre.getName());

        verify(genreRepository, times(1)).findById(TEST_GENRE_ID);
    }

    @Test
    void getGenreById_exception_whenIncorrectId() {
        when(genreRepository.findById(NON_EXISTENT_GENRE_ID)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> genreService.getGenreById(NON_EXISTENT_GENRE_ID));

        verify(genreRepository, times(1)).findById(NON_EXISTENT_GENRE_ID);
    }

    @Test
    void getAllGenres_ListOfGenre_whenTheyExist() {
        when(genreRepository.findAll()).thenReturn(Collections.singletonList(TEST_GENRE));

        List<Genre> allGenres = genreService.getAllGenres();

        assertNotNull(allGenres);
        assertEquals(1, allGenres.size());
        assertEquals(TEST_GENRE.getId(), allGenres.get(0).getId());

        verify(genreRepository, times(1)).findAll();
    }

    @Test
    void deleteGenre_nothing_whenGenreExist() {
        when(genreRepository.existsById(TEST_GENRE_ID)).thenReturn(true);

        assertDoesNotThrow(() -> genreService.deleteGenre(TEST_GENRE_ID));

        verify(genreRepository, times(1)).existsById(TEST_GENRE_ID);
        verify(genreRepository, times(1)).deleteById(TEST_GENRE_ID);
    }

    @Test
    void deleteGenre_exception_whenGenreNotExist() {
        when(genreRepository.existsById(NON_EXISTENT_GENRE_ID)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> genreService.deleteGenre(NON_EXISTENT_GENRE_ID));

        verify(genreRepository, times(1)).existsById(NON_EXISTENT_GENRE_ID);
        verify(genreRepository, never()).deleteById(NON_EXISTENT_GENRE_ID);
    }
}
