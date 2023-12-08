package com.epam.esm;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class BookstoreAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookstoreAppApplication.class, args);
    }
}
