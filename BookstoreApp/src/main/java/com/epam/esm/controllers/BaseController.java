package com.epam.esm.controllers;

import com.epam.esm.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
@Slf4j
public abstract class BaseController {
    protected void validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Exception while validation body in fields: {}", bindingResult.getFieldErrors());
            throw new ValidationException("Validation exception");
        }
    }
}
