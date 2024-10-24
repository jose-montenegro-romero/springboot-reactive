package com.nequi.demo.utils;

import com.nequi.demo.model.Book;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

public class ValidationUtils {

    @Autowired
    private Validator validator;

    public Mono<Book> validateObject(Book book) {
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        if (!violations.isEmpty()) {
            return Mono.error(new RuntimeException(
                    violations.stream()
                            .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                            .collect(Collectors.joining(", "))
            ));
        }
        return Mono.just(book);
    }
}
