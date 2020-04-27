package com.example.webfluxdemo.serde;

import com.example.webfluxdemo.model.Person;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Optional;

public class PersonReader {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static Optional<Person> read(String value) {
        try {
            final JsonNode node = mapper.readTree(value);

            return Optional.of(new Person(
                    node.get("id").asText(),
                    node.get("firstName").asText(),
                    node.get("familyName").asText(),
                    node.get("age").asInt()
            ));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

}