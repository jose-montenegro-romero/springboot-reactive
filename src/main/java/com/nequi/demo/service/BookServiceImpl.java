package com.nequi.demo.service;

import com.nequi.demo.model.Book;
import com.nequi.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Flux<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Mono<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Mono<Book> saveUpdate(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Mono<Book> save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return bookRepository.deleteById(id);
    }
}
