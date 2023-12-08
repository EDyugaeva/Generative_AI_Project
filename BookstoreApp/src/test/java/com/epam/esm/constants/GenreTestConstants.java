package com.epam.esm.constants;

import com.epam.esm.model.Book;
import com.epam.esm.model.Genre;

import static com.epam.esm.constants.AuthorTestConstants.TEST_AUTHOR;

public class GenreTestConstants {
    public static final long TEST_GENRE_ID = 1L;
    public static final long NON_EXISTENT_GENRE_ID = 999L;
    public static final String TEST_GENRE_NAME = "fiction";
    public static final Genre INVALID_GENRE = new Genre(TEST_GENRE_ID, "");

    public static final Genre TEST_GENRE = new Genre(TEST_GENRE_ID, TEST_GENRE_NAME);
}
