package com.nequi.demo.router;

import com.nequi.demo.handler.BookHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;

@Configuration
public class BookRouter {

    private static final String API_BASE_PATH = "/functional/v1";

    @Bean
    public RouterFunction<ServerResponse> functionalRoutes(BookHandler bookHandler){

        return nest(
                path(API_BASE_PATH),
                route(GET("/mono"), bookHandler::mostrarMensajeMono)
                .andRoute(GET("/flux"), bookHandler::mostrarMensajeFlux)
                .andRoute(GET("/books"), bookHandler::getAllBooks)
                .andRoute(GET("/books/{id}"), bookHandler::findById)
                .andRoute(POST("/books"), bookHandler::saveBook)
                .andRoute(PUT("/books/{id}"), bookHandler::saveUpdateBook)
                .andRoute(DELETE("/books/{id}"), bookHandler::deleteById)
        );
    }
}
