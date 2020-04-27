package com.example.webfluxdemo.configuration.router;

import com.example.webfluxdemo.handler.Handler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class Router {

    @Bean
    public RouterFunction<ServerResponse> route(Handler handler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/persons"), handler::handleGetPersonsRequest);
    }

}
