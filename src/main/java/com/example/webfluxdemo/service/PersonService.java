package com.example.webfluxdemo.service;

import com.example.webfluxdemo.model.Person;
import com.example.webfluxdemo.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final Scheduler scheduler;
    private final PersonRepository repository;

    public Flux<Person> getPersons() {
        return Flux
                .fromIterable(repository.findAll())
                .subscribeOn(scheduler);
    }

    public Mono<Person> updatePerson(Person person) {
        return Mono.just(repository.updateOne(person))
                .subscribeOn(scheduler);
    }

    public Mono<Person> createPerson(Person person) {
        return Mono.just(repository.save(person))
                .subscribeOn(scheduler);
    }

    public Mono<Long> deletePerson(Long id) {
        return Mono.just(repository.delete(id))
                .subscribeOn(scheduler);
    }


}
