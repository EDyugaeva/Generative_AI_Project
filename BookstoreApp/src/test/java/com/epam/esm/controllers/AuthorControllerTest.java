package com.epam.esm.controllers;

import com.epam.esm.exception.DataNotFoundException;
import com.epam.esm.repository.AuthorRepository;
import com.epam.esm.services.impl.AuthorServiceImpl;
import com.epam.esm.validators.AuthorValidator;
import org.hibernate.mapping.Any;
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
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.Optional;

import static com.epam.esm.constants.AuthorTestConstants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
public class AuthorControllerTest extends BaseControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private AuthorController authorController;
    @SpyBean
    private AuthorValidator authorValidator;
    @MockBean
    private AuthorRepository authorRepository;
    @SpyBean
    private AuthorServiceImpl authorService;

    @Test
    void contextLoads() {
        assertThat(authorController).isNotNull();
    }
    @Test
    void saveAuthor_StatusCreated_WhenCorrectAuthor() throws Exception {
        when(authorRepository.save(TEST_AUTHOR)).thenReturn(TEST_AUTHOR);

        mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                        .content(asJsonString(TEST_AUTHOR))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(TEST_AUTHOR_ID));

        verify(authorRepository, times(1)).save(TEST_AUTHOR);
    }

    @Test
    void saveAuthor_ValidationException_WhenIncorrectAuthor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                        .content(asJsonString(INVALID_AUTHOR))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(authorRepository, never()).save(ArgumentMatchers.any());
    }

    @Test
    void saveAuthor_ValidationException_WhenIncorrectDateInAuthor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                        .content(asJsonString(INVALID_AUTHOR_BIRTH_YEAR))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(authorRepository, never()).save(ArgumentMatchers.any());
    }

    @Test
    void updateAuthor_statusOk_whenCorrectValues() throws Exception {
        when(authorRepository.findById(TEST_AUTHOR_ID)).thenReturn(Optional.of(TEST_AUTHOR));
        when(authorRepository.save(TEST_AUTHOR)).thenReturn(TEST_AUTHOR);

        mockMvc.perform(MockMvcRequestBuilders.put("/authors/{id}", TEST_AUTHOR_ID)
                        .content(asJsonString(TEST_AUTHOR))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(TEST_AUTHOR_ID));

        verify(authorRepository, times(1)).findById(TEST_AUTHOR_ID);
        verify(authorRepository, times(1)).save(TEST_AUTHOR);
    }

    @Test
    void updateAuthor_NotFound_WhenAuthorDoesNotExist() throws Exception {
        when(authorRepository.findById(TEST_AUTHOR_ID)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.put("/authors/{id}", TEST_AUTHOR_ID)
                        .content(asJsonString(TEST_AUTHOR))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(authorRepository, times(1)).findById(TEST_AUTHOR_ID);
        verify(authorRepository, never()).save(TEST_AUTHOR);
    }

    @Test
    void getAuthorById_StatusOk_WhenGettingCorrectId() throws Exception {
        when(authorRepository.findById(TEST_AUTHOR_ID)).thenReturn(Optional.of(TEST_AUTHOR));

        mockMvc.perform(MockMvcRequestBuilders.get("/authors/{id}", TEST_AUTHOR_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TEST_AUTHOR_ID));

        verify(authorRepository, times(1)).findById(TEST_AUTHOR_ID);
    }

    @Test
    void getAuthorById_Status404_WhenAuthorNotFound() throws Exception {
        when(authorRepository.findById(TEST_AUTHOR_ID)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/authors/{id}", TEST_AUTHOR_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(authorRepository, times(1)).findById(TEST_AUTHOR_ID);
    }

    @Test
    void getAllAuthors_Status200_WhenAuthorsExist() throws Exception {
        when(authorRepository.findAll()).thenReturn(Collections.singletonList(TEST_AUTHOR));

        mockMvc.perform(MockMvcRequestBuilders.get("/authors")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(TEST_AUTHOR_ID));

        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void deleteAuthor_Status204_WhenCorrectId() throws Exception {
        when(authorRepository.existsById(TEST_AUTHOR_ID)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/authors/{id}", TEST_AUTHOR_ID))
                .andExpect(status().isNoContent());

        verify(authorRepository, times(1)).deleteById(TEST_AUTHOR_ID);
    }

    @Test
    void deleteAuthor_Status404_WhenIncorrectId() throws Exception {
        doThrow(new DataNotFoundException("Author not found with ID: " + TEST_AUTHOR_ID))
                .when(authorService).deleteAuthor(TEST_AUTHOR_ID);

        mockMvc.perform(MockMvcRequestBuilders.delete("/author/{id}", TEST_AUTHOR_ID))
                .andExpect(status().isNotFound());

        verify(authorRepository, never()).deleteById(TEST_AUTHOR_ID);
    }
}
