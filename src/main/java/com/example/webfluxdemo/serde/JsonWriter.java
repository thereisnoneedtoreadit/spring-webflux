package com.example.webfluxdemo.serde;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Mono;

public class JsonWriter {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static Mono<String> write(Object value) {
        try {
            return Mono.just(JsonWriter.mapper.writeValueAsString(value));
        } catch (JsonProcessingException e) {
            return Mono.error(e);
        }
    }

}