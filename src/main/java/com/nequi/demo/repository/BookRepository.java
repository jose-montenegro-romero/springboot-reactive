package com.nequi.demo.repository;

import com.nequi.demo.model.Book;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface BookRepository extends R2dbcRepository<Book, Long> {
}
