package com.epam.esm.constants;

import com.epam.esm.model.Book;

import static com.epam.esm.constants.AuthorTestConstants.TEST_AUTHOR;
import static com.epam.esm.constants.GenreTestConstants.TEST_GENRE;

public class BookTestConstants {
    public static final long TEST_BOOK_ID = 1L;
    public static final long NON_EXISTENT_BOOK_ID = 999L;
    public static final String TEST_BOOK_TITLE = "Test Book";

    public static final Book TEST_BOOK = new Book(TEST_BOOK_ID, TEST_BOOK_TITLE, TEST_AUTHOR, TEST_GENRE, 29.99, 10);
    public static final Book INVALID_BOOK = new Book(TEST_BOOK_ID, TEST_BOOK_TITLE, TEST_AUTHOR, TEST_GENRE, 29.99, -1);
}
