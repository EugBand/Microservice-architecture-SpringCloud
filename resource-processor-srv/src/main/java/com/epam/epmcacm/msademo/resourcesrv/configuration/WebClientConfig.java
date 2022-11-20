package com.epam.epmcacm.msademo.resourcesrv.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${api.resource.path}")
    private String resourceServicePath;

    @Value("${api.song.path}")
    private String songServicePath;

    @Value("${proxy.url}")
    private String proxyUrl;

    @Bean("resource-srv")
    public WebClient resourceApiClient() {
        return WebClient.create(proxyUrl + resourceServicePath);
    }

    @Bean("song-srv")
    public WebClient songApiClient() {
        return WebClient.create(proxyUrl + songServicePath);
    }

}
