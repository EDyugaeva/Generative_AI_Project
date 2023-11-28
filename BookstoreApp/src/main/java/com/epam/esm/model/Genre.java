package com.epam.esm.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Table(name = "genres")
@Entity
@Data
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    @NotBlank(message = "Name cannot be blank")
    private String name;
}
