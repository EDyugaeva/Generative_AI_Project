package com.epam.esm.validators;

import com.epam.esm.model.Author;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import org.springframework.validation.Validator;
import java.time.LocalDateTime;

@Component
public class AuthorValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Author.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Author author = (Author) target;
        if (author.getYearOfBirth() != null && author.getYearOfBirth() > LocalDateTime.now().getYear()) {
            errors.rejectValue("yearOfBirth", "author.yearOfBirth.invalid",
                    "Invalid year of birth");
        }
    }
}
