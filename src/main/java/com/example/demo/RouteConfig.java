package com.example.demo;

import cn.hutool.core.collection.CollUtil;
import jakarta.annotation.Resource;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Resource
    ConnectsHolder connectsHolder;

    @Bean
    @RefreshScope
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        var routes = builder.routes();

        var uris = connectsHolder.getUris();

        for (int i = 0; i < uris.size(); i++) {
            final int j = i;

            routes.route(
                r -> r.predicate(serverWebExchange -> {
                        var queryParams = serverWebExchange.getRequest().getQueryParams();

                        var devIds = queryParams.get("devId");

                        if (CollUtil.isEmpty(devIds)) return false;

                        return devIds.get(0).hashCode() % j == 0;
                    })
                    .uri(uris.get(j))
            );
        }

        return routes.build();
    }
}
