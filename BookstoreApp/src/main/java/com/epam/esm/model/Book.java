package com.epam.esm.model;

import javax.persistence.*;
import javax.validation.constraints.Positive;

import lombok.Data;
import lombok.ToString;


@Entity
@Table(name = "books")
@Data
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
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
