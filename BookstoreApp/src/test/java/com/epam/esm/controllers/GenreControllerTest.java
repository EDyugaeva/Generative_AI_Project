package com.epam.esm.controllers;

import com.epam.esm.exception.DataNotFoundException;
import com.epam.esm.repository.GenreRepository;
import com.epam.esm.services.impl.GenreServiceImpl;
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

import static com.epam.esm.constants.GenreTestConstants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreController.class)
public class GenreControllerTest extends BaseControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GenreRepository genreRepository;
    @SpyBean
    private GenreServiceImpl genreService;
    @InjectMocks
    private GenreController genreController;


    @Test
    void contextLoads() {
        assertThat(genreController).isNotNull();
    }

    @Test
    void saveGenre_statusCreated_whenCorrectGenre() throws Exception {
        when(genreService.saveGenre(TEST_GENRE)).thenReturn(TEST_GENRE);

        mockMvc.perform(MockMvcRequestBuilders.post("/genre")
                        .content(asJsonString(TEST_GENRE))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(TEST_GENRE_ID));

        verify(genreRepository, times(1)).save(TEST_GENRE);
    }

    @Test
    void saveGenre_ValidationException_whenIncorrectGenre() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/genre")
                        .content(asJsonString(INVALID_GENRE))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(genreRepository, never()).save(ArgumentMatchers.any());
    }

    @Test
    void updateGenre_statusOk_whenCorrectGenre() throws Exception {
        when(genreRepository.findById(TEST_GENRE_ID)).thenReturn(Optional.of(TEST_GENRE));
        when(genreRepository.save(TEST_GENRE)).thenReturn(TEST_GENRE);

        mockMvc.perform(MockMvcRequestBuilders.put("/genre/{id}", TEST_GENRE_ID)
                        .content(asJsonString(TEST_GENRE))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(TEST_GENRE_ID));

        verify(genreRepository, times(1)).findById(TEST_GENRE_ID);
        verify(genreRepository, times(1)).save(TEST_GENRE);
    }


    @Test
    void updateGenre_NotFound_WhenGenreDoesNotExist() throws Exception {
        when(genreRepository.findById(TEST_GENRE_ID)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.put("/genre/{id}", TEST_GENRE_ID)
                        .content(asJsonString(TEST_GENRE))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(genreRepository, times(1)).findById(TEST_GENRE_ID);
        verify(genreRepository, never()).save(TEST_GENRE);
    }

    @Test
    void getGenreById_StatusOk_WhenGettingCorrectId() throws Exception {
        when(genreRepository.findById(TEST_GENRE_ID)).thenReturn(Optional.of(TEST_GENRE));

        mockMvc.perform(MockMvcRequestBuilders.get("/genre/{id}", TEST_GENRE_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TEST_GENRE_ID));

        verify(genreRepository, times(1)).findById(TEST_GENRE_ID);
    }

    @Test
    void getGenreById_Status404_WhenGenreNotFound() throws Exception {
        when(genreRepository.findById(TEST_GENRE_ID)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/genre/{id}", TEST_GENRE_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(genreRepository, times(1)).findById(TEST_GENRE_ID);
    }

    @Test
    void getAllGenres_Status200_WhenGenresExist() throws Exception {
        when(genreRepository.findAll()).thenReturn(Collections.singletonList(TEST_GENRE));

        mockMvc.perform(MockMvcRequestBuilders.get("/genre")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(TEST_GENRE_ID));

        verify(genreRepository, times(1)).findAll();
    }

    @Test
    void deleteGenre_Status204_WhenCorrectId() throws Exception {
        when(genreRepository.existsById(TEST_GENRE_ID)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/genre/{id}", TEST_GENRE_ID))
                .andExpect(status().isNoContent());

        verify(genreRepository, times(1)).deleteById(TEST_GENRE_ID);
    }

    @Test
    void deleteGenre_Status404_WhenIncorrectId() throws Exception {
        doThrow(new DataNotFoundException("Genre not found with ID: " + TEST_GENRE_ID))
                .when(genreService).deleteGenre(TEST_GENRE_ID);

        mockMvc.perform(MockMvcRequestBuilders.delete("/genre/{id}", TEST_GENRE_ID))
                .andExpect(status().isNotFound());

        verify(genreRepository, never()).deleteById(TEST_GENRE_ID);
    }
}
