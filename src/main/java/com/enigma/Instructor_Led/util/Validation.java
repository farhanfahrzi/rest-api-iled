package com.enigma.Instructor_Led.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Validation {
    private final Validator validator;

    public void validate(Object object){
        Set<ConstraintViolation<Object>> validate = validator.validate(object);
        if(!validate.isEmpty()){
            throw  new ConstraintViolationException(validate);
        }
    }

}
