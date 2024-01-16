package com.example.demo;

import cn.hutool.core.collection.CollUtil;
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

                            return CollUtil.contains(queryParams.get("color"), "red");
                        })
                        .uri("ws://127.0.0.1:3000")
                )
                .build();
        } else {
            return builder.routes()
                .route(
                    r -> r.predicate(serverWebExchange -> {
                            var queryParams = serverWebExchange.getRequest().getQueryParams();

                            return CollUtil.contains(queryParams.get("color"), "yellow");
                        })
                        .uri("ws://127.0.0.1:3001")
                )
                .build();
        }
    }
}
