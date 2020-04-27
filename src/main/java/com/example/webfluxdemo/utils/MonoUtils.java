package com.example.webfluxdemo.utils;

import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.function.Supplier;

public class MonoUtils {

    public static <T> Mono<T> fromOptional(Optional<T> option, Supplier<? extends Exception> errorSupplier) {
        return option.map(Mono::just).orElseGet(() -> Mono.error(errorSupplier.get()));
    }

}
