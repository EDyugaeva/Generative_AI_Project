package com.epam.esm.repository;

import com.epam.esm.model.Book;
import com.epam.esm.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByAuthorFirstNameAndAuthorLastName(String authorFirstName, String authorLastName);

    List<Book> findByGenreName(String genreName);
}
