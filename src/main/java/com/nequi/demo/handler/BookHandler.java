package com.nequi.demo.handler;

import com.nequi.demo.model.Book;
import com.nequi.demo.service.BookService;
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

    private Mono<ServerResponse> response404 = ServerResponse.notFound().build();
    private Mono<ServerResponse> response406 = ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).build();

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
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookService.findAll(), Book.class);
    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return bookService.findById(Long.valueOf(id))
                .flatMap(book -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(book))
                )
                .switchIfEmpty(response404);
    }
}
