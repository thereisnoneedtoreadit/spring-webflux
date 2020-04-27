package com.example.webfluxdemo.service;

import com.example.webfluxdemo.model.Person;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PersonService {

    private final List<Person> persons;

    public PersonService() {
        this.persons = Stream.of(new Person("id", "foo", "bar", 1),new Person("id", "foo", "bar", 1))
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
    }

    public Flux<Person> getPersons() {
        return Flux.fromIterable(this.persons);
    }

}
