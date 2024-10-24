package com.nequi.demo.controller;

import com.nequi.demo.model.Book;
import com.nequi.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    private BookService bookService;

//    private final BookService bookService;
//
//    public BookController(BookService bookService) {
//        this.bookService = bookService;
//    }

//    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @GetMapping()
    public Mono<ResponseEntity<Flux<Book>>> getAllBooks() {
        return Mono.just(
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON) // APPLICATION_JSON_UTF8
                        .body(bookService.findAll())
        );
    }

    @GetMapping(value = "/{id}")
    public Mono<ResponseEntity<Book>> findById(@PathVariable Long id) {
        System.out.println("El id es:" +id);
        return bookService.findById(id)
                .map(c -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Book> save(@RequestBody Book book) {
        return bookService.save(book);
    }

    @DeleteMapping(value = "/{id}")
    public Mono<Void> deleteById(@PathVariable Long id) {
        return bookService.deleteById(id);
    }

}
