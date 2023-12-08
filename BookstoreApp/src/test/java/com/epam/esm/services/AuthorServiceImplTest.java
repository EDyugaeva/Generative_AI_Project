package com.epam.esm.services;

import com.epam.esm.exception.DataNotFoundException;
import com.epam.esm.model.Author;
import com.epam.esm.repository.AuthorRepository;
import com.epam.esm.services.impl.AuthorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.constants.AuthorTestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceImplTest {
    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    void saveAuthor_Author_whenCorrect() {
        when(authorRepository.save(TEST_AUTHOR)).thenReturn(TEST_AUTHOR);

        Author savedAuthor = authorService.saveAuthor(TEST_AUTHOR);

        assertNotNull(savedAuthor);
        assertEquals(TEST_AUTHOR.getId(), savedAuthor.getId());
        assertEquals(TEST_AUTHOR.getFirstName(), savedAuthor.getFirstName());
        assertEquals(TEST_AUTHOR.getLastName(), savedAuthor.getLastName());
        assertEquals(TEST_AUTHOR.getYearOfBirth(), savedAuthor.getYearOfBirth());
        assertEquals(TEST_AUTHOR.getCountryOfResidence(), savedAuthor.getCountryOfResidence());

        verify(authorRepository, times(1)).save(TEST_AUTHOR);
    }

    @Test
    void updateAuthor_Author_whenCorrect() {
        when(authorRepository.save(TEST_AUTHOR)).thenReturn(TEST_AUTHOR);

        Author updatedAuthor = authorService.updateAuthor(TEST_AUTHOR);

        assertNotNull(updatedAuthor);
        assertEquals(TEST_AUTHOR.getId(), updatedAuthor.getId());
        assertEquals(TEST_AUTHOR.getFirstName(), updatedAuthor.getFirstName());
        assertEquals(TEST_AUTHOR.getLastName(), updatedAuthor.getLastName());

        verify(authorRepository, times(1)).save(TEST_AUTHOR);
    }

    @Test
    void getAuthorById_Author_whenCorrectId() {
        when(authorRepository.findById(TEST_AUTHOR_ID)).thenReturn(Optional.of(TEST_AUTHOR));

        Author foundAuthor = authorService.getAuthorById(TEST_AUTHOR_ID);

        assertNotNull(foundAuthor);
        assertEquals(TEST_AUTHOR.getId(), foundAuthor.getId());
        assertEquals(TEST_AUTHOR.getFirstName(), foundAuthor.getFirstName());
        assertEquals(TEST_AUTHOR.getLastName(), foundAuthor.getLastName());

        verify(authorRepository, times(1)).findById(TEST_AUTHOR_ID);
    }

    @Test
    void getAuthorById_exception_whenIncorrectId() {
        when(authorRepository.findById(NON_EXISTENT_AUTHOR_ID)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> authorService.getAuthorById(NON_EXISTENT_AUTHOR_ID));

        verify(authorRepository, times(1)).findById(NON_EXISTENT_AUTHOR_ID);
    }

    @Test
    void getAllAuthors_ListOfAuthor_whenTheyExist() {
        when(authorRepository.findAll()).thenReturn(Collections.singletonList(TEST_AUTHOR));

        List<Author> allAuthors = authorService.getAllAuthors();

        assertNotNull(allAuthors);
        assertEquals(1, allAuthors.size());
        assertEquals(TEST_AUTHOR.getId(), allAuthors.get(0).getId());

        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void deleteAuthor_nothing_whenAuthorExist() {
        when(authorRepository.existsById(TEST_AUTHOR_ID)).thenReturn(true);

        assertDoesNotThrow(() -> authorService.deleteAuthor(TEST_AUTHOR_ID));

        verify(authorRepository, times(1)).existsById(TEST_AUTHOR_ID);
        verify(authorRepository, times(1)).deleteById(TEST_AUTHOR_ID);
    }

    @Test
    void deleteAuthor_exception_whenAuthorNotExist() {
        when(authorRepository.existsById(NON_EXISTENT_AUTHOR_ID)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> authorService.deleteAuthor(NON_EXISTENT_AUTHOR_ID));

        verify(authorRepository, times(1)).existsById(NON_EXISTENT_AUTHOR_ID);
        verify(authorRepository, never()).deleteById(NON_EXISTENT_AUTHOR_ID);
    }
}
