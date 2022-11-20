package com.epam.epmcacm.msademo.resourcesrv.service;

import com.epam.epmcacm.msademo.resourcesrv.dto.StorageDto;
import com.epam.epmcacm.msademo.resourcesrv.dto.StorageType;
import com.epam.epmcacm.msademo.resourcesrv.exception.BadRequestException;
import com.epam.epmcacm.msademo.resourcesrv.exception.ServiceAvailableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class StorageService {

    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(2);

    public static final int MAX_ATTEMPTS = 3;

    public static final String FAILED_TO_PROCESS_AFTER_MAX_RETRIES = "External service failed to process after max retries";

    private final WebClient storageApiClient;

    @Autowired
    public StorageService(WebClient storageApiClient) {
        this.storageApiClient = storageApiClient;
    }

   @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "getDefaultStorage")
    public StorageDto fetchStorageByType(StorageType storageType){
       log.info("Start fetching storage with type {}", storageType);
        List<StorageDto> storageDtos = this.getStorages();
        return storageDtos.stream()
                .filter(s -> s.getStorageType().equals(storageType)).findFirst()
                .orElseThrow(() -> new BadRequestException("Wrong staging bucket"));
    }

    public StorageDto fetchStorageByStorageId(String storageId){
        log.info("Start fetching storage with id {}", storageId);
        List<StorageDto> storageDtos = this.getStorages();
        return storageDtos.stream().filter(s -> s.getId().equals(storageId))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Wrong storage id"));
    }

    public List<StorageDto> getStorages() {
        log.info("Start fetching storages");
        List<StorageDto> storageDtos = storageApiClient
                .get()
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<StorageDto>>() {

                })
                .retryWhen(Retry.backoff(MAX_ATTEMPTS, REQUEST_TIMEOUT)
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                            throw new ServiceAvailableException(FAILED_TO_PROCESS_AFTER_MAX_RETRIES,
                                    HttpStatus.SERVICE_UNAVAILABLE.value());
                        }))
                .block();
        return storageDtos;
    }

    public StorageDto getDefaultStorage(Throwable t){
        log.error("Inside fallbackForGetSeller, cause - {}", t.toString());
        return StorageDto.builder()
                .id(UUID.randomUUID().toString())
                .storageType(StorageType.RESERVE)
                .bucketName("temp")
                .path("/")
                .build();
    }

}
