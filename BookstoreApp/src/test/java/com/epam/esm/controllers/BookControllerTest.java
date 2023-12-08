package com.epam.esm.controllers;

import com.epam.esm.repository.BookRepository;
import com.epam.esm.services.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.Optional;

import static com.epam.esm.constants.AuthorTestConstants.TEST_AUTHOR_FIRST_NAME;
import static com.epam.esm.constants.AuthorTestConstants.TEST_AUTHOR_LAST_NAME;
import static com.epam.esm.constants.BookTestConstants.*;
import static com.epam.esm.constants.GenreTestConstants.TEST_GENRE;
import static com.epam.esm.constants.GenreTestConstants.TEST_GENRE_NAME;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)

public class BookControllerTest extends BaseControllerTest{
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookRepository bookRepository;
    @SpyBean
    private BookServiceImpl bookService;
    @InjectMocks
    private BookController bookController;


    @Test
    void contextLoads() {
        assertThat(bookController).isNotNull();
    }
    @Test
    void saveBook_StatusCreated_WhenCorrectBook() throws Exception {
        when(bookRepository.save(TEST_BOOK)).thenReturn(TEST_BOOK);

        mockMvc.perform(MockMvcRequestBuilders.post("/books")
                        .content(asJsonString(TEST_BOOK))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(TEST_BOOK_ID));

        verify(bookRepository, times(1)).save(TEST_BOOK);
    }

    @Test
    void saveBook_ValidationException_WhenIncorrectBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/books")
                        .content(asJsonString(INVALID_BOOK))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(bookRepository, never()).save(ArgumentMatchers.any());
    }

    @Test
    void updateBook_PositiveCase() throws Exception {
        when(bookRepository.findById(TEST_BOOK_ID)).thenReturn(Optional.of(TEST_BOOK));
        when(bookRepository.save(TEST_BOOK)).thenReturn(TEST_BOOK);

        mockMvc.perform(MockMvcRequestBuilders.put("/books/{id}", TEST_BOOK_ID)
                        .content(asJsonString(TEST_BOOK))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(TEST_BOOK_ID));

        verify(bookRepository, times(1)).findById(TEST_BOOK_ID);
        verify(bookRepository, times(1)).save(TEST_BOOK);
    }

    @Test
    void getBookById_StatusOk_WhenGettingCorrectId() throws Exception {
        when(bookRepository.findById(TEST_BOOK_ID)).thenReturn(Optional.of(TEST_BOOK));

        mockMvc.perform(MockMvcRequestBuilders.get("/books/{id}", TEST_BOOK_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TEST_BOOK_ID));

        verify(bookRepository, times(1)).findById(TEST_BOOK_ID);
    }

    @Test
    void getBookById_Status404_WhenBookNotFound() throws Exception {
        when(bookRepository.findById(TEST_BOOK_ID)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/books/{id}", TEST_BOOK_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(bookRepository, times(1)).findById(TEST_BOOK_ID);
    }

    @Test
    void getAllBooks_Status200_WhenBooksExist() throws Exception {
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(TEST_BOOK));

        mockMvc.perform(MockMvcRequestBuilders.get("/books")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(TEST_BOOK_ID));

        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void deleteBook_Status204_WhenCorrectId() throws Exception {
        when(bookRepository.existsById(TEST_BOOK_ID)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/books/{id}", TEST_BOOK_ID))
                .andExpect(status().isNoContent());

        verify(bookRepository, times(1)).deleteById(TEST_BOOK_ID);
    }

    @Test
    void searchByTitle_StatusOk_WhenCorrectTitle() throws Exception {
        when(bookRepository.findByTitleContainingIgnoreCase(TEST_BOOK_TITLE))
                .thenReturn(Collections.singletonList(TEST_BOOK));

        mockMvc.perform(MockMvcRequestBuilders.get("/books/searchByTitle")
                        .param("title", TEST_BOOK_TITLE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(TEST_BOOK_ID));

        verify(bookService, times(1)).searchByTitle(TEST_BOOK_TITLE);
    }

    @Test
    void searchByAuthor_StatusOk_WhenCorrectAuthor() throws Exception {
        when(bookRepository.findByAuthorFirstNameAndAuthorLastName(TEST_AUTHOR_FIRST_NAME, TEST_AUTHOR_LAST_NAME))
                .thenReturn(Collections.singletonList(TEST_BOOK));

        mockMvc.perform(MockMvcRequestBuilders.get("/books/searchByAuthor")
                        .param("authorFirstName", TEST_AUTHOR_FIRST_NAME)
                        .param("authorLastName", TEST_AUTHOR_LAST_NAME)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(TEST_BOOK_ID));

        verify(bookService, times(1)).searchByAuthor(TEST_AUTHOR_FIRST_NAME, TEST_AUTHOR_LAST_NAME);
    }

    @Test
    void searchByGenre_StatusOk_WhenCorrectGenre() throws Exception {
        when(bookRepository.findByGenreName(TEST_GENRE_NAME)).thenReturn(Collections.singletonList(TEST_BOOK));

        mockMvc.perform(MockMvcRequestBuilders.get("/books/searchByGenre")
                        .param("genre", TEST_GENRE_NAME)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(TEST_BOOK_ID));

        verify(bookService, times(1)).searchByGenre(TEST_GENRE_NAME);
    }
}
