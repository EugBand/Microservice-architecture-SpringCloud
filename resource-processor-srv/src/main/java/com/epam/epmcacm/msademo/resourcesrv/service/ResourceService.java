package com.epam.epmcacm.msademo.resourcesrv.service;

import com.epam.epmcacm.msademo.resourcesrv.dto.ResourceDto;
import com.epam.epmcacm.msademo.resourcesrv.exception.ServiceAvailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
@Slf4j
public class ResourceService {

    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(3);

    public static final int MAX_ATTEMPTS = 3;

    public static final String FAILED_TO_PROCESS_AFTER_MAX_RETRIES = "External Service failed to process after max retries";

    private final WebClient resourceApiClient;

    @Autowired
    public ResourceService(@Qualifier("resource-srv") WebClient resourceApiClient) {
        this.resourceApiClient = resourceApiClient;
    }

    public ResourceDto getResource(String id) {
        return resourceApiClient
                .get()
                .uri(id)
                .retrieve()
                .bodyToMono(ResourceDto.class)
                .retryWhen(Retry.backoff(MAX_ATTEMPTS, REQUEST_TIMEOUT)
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                    throw new ServiceAvailableException(FAILED_TO_PROCESS_AFTER_MAX_RETRIES, HttpStatus.SERVICE_UNAVAILABLE.value());}))
                .block();
    }
}
