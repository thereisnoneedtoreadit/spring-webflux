package com.example.webfluxdemo.exception;

public class InvalidRequestBodyException extends BadRequestException {

    public InvalidRequestBodyException(Class<?> expectedClass) {
        super("The provided request body is invalid. Expected a " + expectedClass.getSimpleName() + " element.");
    }

}
