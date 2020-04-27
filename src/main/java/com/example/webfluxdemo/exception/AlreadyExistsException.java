package com.example.webfluxdemo.exception;

public class AlreadyExistsException extends Exception {

    public AlreadyExistsException() {}

    public AlreadyExistsException(String message) {
        super(message);
    }

}
