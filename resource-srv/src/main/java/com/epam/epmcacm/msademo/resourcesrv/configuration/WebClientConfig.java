package com.epam.epmcacm.msademo.resourcesrv.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${api.storage.path}")
    private String storageServicePath;

    @Value("${proxy.url}")
    private String proxyUrl;

    @Bean("storage-srv")
    public WebClient songApiClient() {
        return WebClient.create(proxyUrl + storageServicePath);
    }
}
