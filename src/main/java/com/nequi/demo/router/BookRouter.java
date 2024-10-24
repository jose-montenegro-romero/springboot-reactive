package com.nequi.demo.router;

import com.nequi.demo.handler.BookHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class BookRouter {

    @Bean
    public RouterFunction<ServerResponse> functionalRoutes(BookHandler bookHandler){

        return RouterFunctions
                .route(GET("/functional/mono"), bookHandler::mostrarMensajeMono)
                .andRoute(GET("/functional/flux"), bookHandler::mostrarMensajeFlux)
                .andRoute(GET("/functional/books"), bookHandler::getAllBooks)
                .andRoute(GET("/functional/books/{id}"), bookHandler::findById);
    }
}
