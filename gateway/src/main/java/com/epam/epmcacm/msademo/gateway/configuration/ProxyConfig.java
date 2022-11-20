package com.epam.epmcacm.msademo.gateway.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProxyConfig {
    @Value("${api.resource.path}")
    String resourcePath;
    @Value("${api.song.path}")
    String songPath;
    @Value("${api.storage.path}")
    String storagePath;
    @Value("${api.processor.path}")
    String processorPath;
    @Value("${api.processor.url}")
    String processorUrl;
    @Value("${api.resource.url}")
    String resourceUrl;
    @Value("${api.song.url}")
    String songUrl;
    @Value("${api.storage.url}")
    String storageUrl;

    private static final String PATH_EXPANDER = "**";

    @Bean
    RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("resource-route",
                        route -> route.path(resourcePath + PATH_EXPANDER)
                                .uri(resourceUrl))
                .route("song-route",
                        route -> route.path(songPath + PATH_EXPANDER)
                                .uri(songUrl))
                .route("processor-route",
                        route -> route.path(processorPath + PATH_EXPANDER)
                                .uri(processorUrl))
                .route("storage-route",
                        route -> route.path(storagePath + PATH_EXPANDER)
                                .uri(storageUrl))
                .build();
    }
}
