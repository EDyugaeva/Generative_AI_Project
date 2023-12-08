package com.epam.esm.constants;

import com.epam.esm.model.Author;

import java.time.LocalDateTime;

public class AuthorTestConstants {
    public static final long TEST_AUTHOR_ID = 1L;
    public static final long NON_EXISTENT_AUTHOR_ID = 999L;
    public static final String TEST_AUTHOR_FIRST_NAME = "John";
    public static final String TEST_AUTHOR_LAST_NAME = "Doe";


    public static final Author TEST_AUTHOR = new Author(TEST_AUTHOR_ID, TEST_AUTHOR_FIRST_NAME, TEST_AUTHOR_LAST_NAME,1980, "USA" );
    public static final Author INVALID_AUTHOR_BIRTH_YEAR = new Author(TEST_AUTHOR_ID, TEST_AUTHOR_FIRST_NAME, TEST_AUTHOR_LAST_NAME, LocalDateTime.now().getYear() + 1, "USA" );
    public static final Author INVALID_AUTHOR = new Author(TEST_AUTHOR_ID, "", "",1980, "USA" );
}
