package com.example.webfluxdemo.controller;

import com.example.webfluxdemo.exception.AlreadyExistsException;
import com.example.webfluxdemo.exception.InvalidRequestBodyException;
import com.example.webfluxdemo.exception.NotFoundResourceException;
import com.example.webfluxdemo.model.Person;
import com.example.webfluxdemo.serde.JsonWriter;
import com.example.webfluxdemo.serde.PersonReader;
import com.example.webfluxdemo.service.PersonService;
import com.example.webfluxdemo.utils.MonoUtils;
import com.example.webfluxdemo.utils.Responses;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Component
@RequiredArgsConstructor
public class Handler {

    private final PersonService personService;
    private final Scheduler scheduler;

    public Mono<ServerResponse> handleGetPersonsRequest(ServerRequest request) {
        return personService
                .getPersons()
                .collectList()
                .flatMap(JsonWriter::write)
                .flatMap(json -> ServerResponse.ok().body(Mono.just(json), String.class))
                .onErrorResume(JsonProcessingException.class, Responses::internalServerError)
                .subscribeOn(scheduler);
    }

    public Mono<ServerResponse> handleUpdatePersonRequest(ServerRequest request) {
        return request
                .bodyToMono(String.class)
                .flatMap(this::readPersonFromRequestBody)
                .filter(person -> person.getId() == Long.parseLong(request.pathVariable("id")))
                .flatMap(personService::updatePerson)
                .flatMap(JsonWriter::write)
                .flatMap(json -> ServerResponse.ok().body(Mono.just(json), String.class))
                .onErrorResume(NotFoundResourceException.class, Responses::notFound)
                .onErrorResume(InvalidRequestBodyException.class, Responses::badRequest)
                .switchIfEmpty(Responses.badRequest(new InvalidRequestBodyException(Person.class)))
                .subscribeOn(scheduler);
    }

    public Mono<ServerResponse> handleCreatePersonRequest(ServerRequest request) {
        return request
                .bodyToMono(String.class)
                .flatMap(this::readPersonFromRequestBody)
                .flatMap(personService::createPerson)
                .flatMap(JsonWriter::write)
                .flatMap(json -> ServerResponse.ok().body(Mono.just(json), String.class))
                .onErrorResume(InvalidRequestBodyException.class, Responses::badRequest)
                .onErrorResume(AlreadyExistsException.class, Responses::internalServerError)
                .switchIfEmpty(Responses.badRequest(new InvalidRequestBodyException(Person.class)))
                .subscribeOn(scheduler);
    }

    public Mono<ServerResponse> handleDeletePersonRequest(ServerRequest request) {
        return personService
                .deletePerson(Long.parseLong(request.pathVariable("id")))
                .flatMap(JsonWriter::write)
                .flatMap(json -> ServerResponse.ok().body(Mono.just(json), String.class))
                .onErrorResume(NotFoundResourceException.class, Responses::notFound)
                .switchIfEmpty(Responses.badRequest(new InvalidRequestBodyException(Person.class)))
                .subscribeOn(scheduler);
    }

    private Mono<Person> readPersonFromRequestBody(String body) {
        return MonoUtils.fromOptional(
                PersonReader.read(body),
                () -> new InvalidRequestBodyException(Person.class)
        );
    }

}
