package com.epam.esm.services;

import com.epam.esm.exception.DataNotFoundException;
import com.epam.esm.model.Book;
import com.epam.esm.repository.BookRepository;
import com.epam.esm.services.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.constants.BookTestConstants.*;
import static com.epam.esm.constants.AuthorTestConstants.*;
import static com.epam.esm.constants.GenreTestConstants.TEST_GENRE;
import static com.epam.esm.constants.GenreTestConstants.TEST_GENRE_NAME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void saveBook_BookInstant_whenSavingCorrectBook() {
        when(bookRepository.save(TEST_BOOK)).thenReturn(TEST_BOOK);

        Book savedBook = bookService.saveBook(TEST_BOOK);

        assertNotNull(savedBook);
        assertEquals(TEST_BOOK.getId(), savedBook.getId());
        assertEquals(TEST_BOOK.getTitle(), savedBook.getTitle());
        assertEquals(TEST_BOOK.getAuthor(), savedBook.getAuthor());
        assertEquals(TEST_BOOK.getGenre(), savedBook.getGenre());

        verify(bookRepository, times(1)).save(TEST_BOOK);
    }

    @Test
    void updateBook_BookInstant_whenSavingCorrectBook() {
        when(bookRepository.save(TEST_BOOK)).thenReturn(TEST_BOOK);

        Book updatedBook = bookService.update(TEST_BOOK);

        assertNotNull(updatedBook);
        assertEquals(TEST_BOOK.getId(), updatedBook.getId());
        assertEquals(TEST_BOOK.getTitle(), updatedBook.getTitle());

        verify(bookRepository, times(1)).save(TEST_BOOK);
    }

    @Test
    void getBookById_Book_whenCorrectId() {
        when(bookRepository.findById(TEST_BOOK_ID)).thenReturn(Optional.of(TEST_BOOK));
        Book foundBook = bookService.getBookById(TEST_BOOK_ID);

        assertNotNull(foundBook);
        assertEquals(TEST_BOOK.getId(), foundBook.getId());
        assertEquals(TEST_BOOK.getTitle(), foundBook.getTitle());

        verify(bookRepository, times(1)).findById(TEST_BOOK_ID);
    }

    @Test
    void getBookById_exception_whenIncorrectId() {
        when(bookRepository.findById(NON_EXISTENT_BOOK_ID)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> bookService.getBookById(NON_EXISTENT_BOOK_ID));

        verify(bookRepository, times(1)).findById(NON_EXISTENT_BOOK_ID);
    }

    @Test
    void getAllBooks_ListOfBook_whenTheyExist() {
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(TEST_BOOK));

        List<Book> allBooks = bookService.getAllBooks();

        assertNotNull(allBooks);
        assertEquals(1, allBooks.size());
        assertEquals(TEST_BOOK.getId(), allBooks.get(0).getId());
        // Add more assertions as needed

        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void deleteBook_nothing_whenBookExist() {
        when(bookRepository.existsById(TEST_BOOK_ID)).thenReturn(true);

        assertDoesNotThrow(() -> bookService.deleteBook(TEST_BOOK_ID));

        verify(bookRepository, times(1)).existsById(TEST_BOOK_ID);
        verify(bookRepository, times(1)).deleteById(TEST_BOOK_ID);
    }

    @Test
    void deleteBook_exception_whenBookNotExist() {
        when(bookRepository.existsById(NON_EXISTENT_BOOK_ID)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> bookService.deleteBook(NON_EXISTENT_BOOK_ID));

        verify(bookRepository, times(1)).existsById(NON_EXISTENT_BOOK_ID);
        verify(bookRepository, never()).deleteById(NON_EXISTENT_BOOK_ID);
    }

    @Test
    void searchByTitle_ListBook_whenSearching() {
        when(bookRepository.findByTitleContainingIgnoreCase(TEST_BOOK_TITLE))
                .thenReturn(Collections.singletonList(TEST_BOOK));

        List<Book> foundBooks = bookService.searchByTitle(TEST_BOOK_TITLE);

        assertNotNull(foundBooks);
        assertEquals(1, foundBooks.size());
        assertEquals(TEST_BOOK.getId(), foundBooks.get(0).getId());

        verify(bookRepository, times(1)).findByTitleContainingIgnoreCase(TEST_BOOK_TITLE);
    }

    @Test
    void searchByAuthor_ListBook_whenSearching() {
        when(bookRepository.findByAuthorFirstNameAndAuthorLastName(TEST_AUTHOR_FIRST_NAME, TEST_AUTHOR_LAST_NAME))
                .thenReturn(Collections.singletonList(TEST_BOOK));

        List<Book> foundBooks = bookService.searchByAuthor(TEST_AUTHOR_FIRST_NAME, TEST_AUTHOR_LAST_NAME);

        assertNotNull(foundBooks);
        assertEquals(1, foundBooks.size());
        assertEquals(TEST_BOOK.getId(), foundBooks.get(0).getId());

        verify(bookRepository, times(1))
                .findByAuthorFirstNameAndAuthorLastName(TEST_AUTHOR_FIRST_NAME, TEST_AUTHOR_LAST_NAME);
    }

    @Test
    void searchByGenre_ListBook_whenSearching() {
        when(bookRepository.findByGenreName(TEST_GENRE_NAME)).thenReturn(Collections.singletonList(TEST_BOOK));

        List<Book> foundBooks = bookService.searchByGenre(TEST_GENRE_NAME);

        assertNotNull(foundBooks);
        assertEquals(1, foundBooks.size());
        assertEquals(TEST_BOOK.getId(), foundBooks.get(0).getId());

        verify(bookRepository, times(1)).findByGenreName(TEST_GENRE_NAME);
    }
}
