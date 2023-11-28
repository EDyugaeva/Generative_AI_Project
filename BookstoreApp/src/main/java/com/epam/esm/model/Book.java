package com.epam.esm.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name = "books")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    @NotBlank(message = "Title cannot be blank")
    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;

    @Column(name = "price")
    @Positive(message = "Price should be positive")
    private Double price;

    @Column(name = "quantity_available")
    @Positive(message = "Quantity available should be positive")
    private Integer quantityAvailable;
}
