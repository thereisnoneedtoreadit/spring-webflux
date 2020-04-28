package com.example.webfluxdemo.serde;

import com.example.webfluxdemo.model.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class PersonReader {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static Optional<Person> read(String value) {
        try {
            return Optional.of(mapper.readValue(value, Person.class));
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }

}