package com.nequi.demo.utils;

import com.nequi.demo.model.Book;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ValidationUtils {

    @Autowired
    private Validator validator;

//    public Mono<Book> validateObject(Book book) {
    public <T> Mono<T> validateObject(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            return Mono.error(new RuntimeException(
                    violations.stream()
                            .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                            .collect(Collectors.joining(", "))
            ));
        }
        return Mono.just(object);
    }
}
