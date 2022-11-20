package com.epam.epmcacm.msademo.resourcesrv.service;

import com.epam.epmcacm.msademo.resourcesrv.dto.ResourceDto;
import com.epam.epmcacm.msademo.resourcesrv.dto.StorageDto;
import com.epam.epmcacm.msademo.resourcesrv.dto.StorageType;
import com.epam.epmcacm.msademo.resourcesrv.mapper.ResourceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class RMQConsumerService {

    @Autowired DBService dbService;

    @Autowired S3Service s3FileService;

    @Autowired ResourceMapper mapper;

    @Autowired StorageService storageService;

    @Bean
    public Consumer<ResourceDto> sink() {
        return value -> {
            log.info("Start changing resource type to permanent for res id {}", value.getId());
            StorageDto stagingStorageDto = storageService.fetchStorageByStorageId(value.getStorageId());
            StorageDto permanentStorageDto = storageService.fetchStorageByType(StorageType.PERMANENT);
            s3FileService.moveFile(value.getId(), stagingStorageDto, permanentStorageDto);
            value.setStorageId(permanentStorageDto.getId());
            log.info("Change resource type to permanent for res id {}", value.getId());
        };

    }

}
