package com.nequi.demo.service;

import com.nequi.demo.model.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService{

    public Flux<Book> findAll();

    public Mono<Book> findById(Long id);

    public Mono<Book> save(Book book);

    public Mono<Void> deleteById(Long id);
}