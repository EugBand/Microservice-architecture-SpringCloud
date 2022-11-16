package com.epam.epmcacm.msademo.gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProxyConfig {
    @Bean
    RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("resource-route",
                        route -> route.path("/api/v1/resources/**")
                                .uri("lb://resource-srv"))
                .route("song-route",
                        route -> route.path("/api/v1/songs/**")
                                .uri("lb://song-srv"))
                .route("storage-route",
                        route -> route.path("/api/v1/storages/**")
                                .uri("lb://storage-srv"))
                .build();
    }
}
