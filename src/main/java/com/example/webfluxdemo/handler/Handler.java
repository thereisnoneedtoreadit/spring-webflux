package com.example.webfluxdemo.handler;

import com.example.webfluxdemo.serde.JsonWriter;
import com.example.webfluxdemo.service.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Component
@RequiredArgsConstructor
public class Handler {

    private final PersonService personService;

    public Mono<ServerResponse> handleGetPersonsRequest(ServerRequest request) {
        return personService
                .getPersons()
                .collectList()
                .flatMap(JsonWriter::write)
                .flatMap(json -> ServerResponse.ok().body(Mono.just(json), String.class))
                .onErrorResume(
                        JsonProcessingException.class,
                        (e) -> ServerResponse.status(INTERNAL_SERVER_ERROR)
                                .body(Mono.just(e.getMessage()), String.class)
                );
    }

}
