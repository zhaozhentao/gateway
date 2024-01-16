package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    private Integer time = 1;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    @RefreshScope
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        if (time == 1) {
            time++;
            return builder.routes()
                .route(
                    r -> r.predicate(serverWebExchange -> {
                        var queryParams = serverWebExchange.getRequest().getQueryParams();
                        var color = queryParams.get("color");
                        return color.contains("red");
                    }).uri("ws://127.0.0.1:3000")
                )
                .build();
        } else {
            return builder.routes()
                .route(
                    r -> r.predicate(serverWebExchange -> {
                        var queryParams = serverWebExchange.getRequest().getQueryParams();
                        var color = queryParams.get("color");
                        return color.contains("yellow");
                    }).uri("ws://127.0.0.1:3001")
                )
                .build();
        }
    }
}
