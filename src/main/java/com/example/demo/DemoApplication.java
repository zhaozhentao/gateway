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

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    @RefreshScope
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route(
                r -> r.predicate(serverWebExchange -> {
                        var queryParams = serverWebExchange.getRequest().getQueryParams();

                        var devIds = queryParams.get("devId");

                        if (CollUtil.isEmpty(devIds)) return false;

                        var devId = devIds.get(0);

                        return devId.hashCode() % 2 == 0;
                    })
                    .uri("ws://127.0.0.1:8012")
            )
            .build();
    }
}
