package com.nequi.demo.handler;

import com.nequi.demo.dto.BookMapper;
import com.nequi.demo.model.Book;
import com.nequi.demo.service.BookService;
import com.nequi.demo.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class BookHandler {

    @Autowired
    private BookService bookService;

    @Autowired
    private ValidationUtils validationUtils;

    private static final Mono<ServerResponse> response404 = ServerResponse.notFound().build();
    private static final Mono<ServerResponse> response406 = ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).build();

    public Mono<ServerResponse> mostrarMensajeMono(ServerRequest serverRequest){
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(
                        Mono.just("Bienvenidos a java reactivo."), String.class
                );
    }

    public Mono<ServerResponse> mostrarMensajeFlux(ServerRequest serverRequest){

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON) // MediaType.APPLICATION_NDJSON
                .body(
                        Flux.just("Bienvenido", "a","java","reactivo.")
                                .delayElements(Duration.ofSeconds(1)), String.class
                );
    }

    public Mono<ServerResponse> getAllBooks(ServerRequest serverRequest) {
//        return ServerResponse
//                .ok()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(bookService.findAll(), Book.class);
            return bookService.findAll()
                    .map(BookMapper.INSTANCE::bookToBookDTO)
                    .collectList()
                    .flatMap(books -> ServerResponse
                            .ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(fromValue(books))
                    );
    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return bookService.findById(Long.valueOf(id))
                .map(BookMapper.INSTANCE::bookToBookDTO)
                .flatMap(book -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(book))
                )
                .switchIfEmpty(response404);
    }

    public Mono<ServerResponse> saveUpdateBook(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        Mono<Book> bookMono = serverRequest.bodyToMono(Book.class);

        return bookMono
                .flatMap(book -> bookService.findById(Long.valueOf(id))
                        .flatMap(existingBook -> {
                            return bookService.save(book)
                                    .flatMap(bookSave -> ServerResponse
                                            .accepted()
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .body(fromValue(bookSave))
                                    );
                        })
                        .switchIfEmpty(Mono.error(new RuntimeException("Libro no encontrado")))
                )
                .switchIfEmpty(response406);
    }

    public Mono<ServerResponse> saveBook(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Book.class)
                .flatMap(validationUtils::validateObject)
                .flatMap(book -> {
                    if (book.getId() == null || book.getId() == 0) {
                        book.setId(null);
                    }
                    return bookService.save(book);
                })
                .flatMap(bookSave -> ServerResponse
                        .accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(bookSave)))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()))
                .switchIfEmpty(response406);
    }

    public Mono<ServerResponse> deleteById(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");

        Mono<Void> deleteBook = bookService.deleteById(Long.valueOf(id))
                .doOnSuccess(v -> System.out.println("Eliminado el libro con id: " + id));

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(deleteBook, Void.class);
    }

}
