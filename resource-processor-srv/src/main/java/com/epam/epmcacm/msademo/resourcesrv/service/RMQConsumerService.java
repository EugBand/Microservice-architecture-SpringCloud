package com.epam.epmcacm.msademo.resourcesrv.service;

import com.epam.epmcacm.msademo.resourcesrv.dto.MetadataDto;
import com.epam.epmcacm.msademo.resourcesrv.dto.ProcessorMetadataDto;
import com.epam.epmcacm.msademo.resourcesrv.dto.ResourceDto;
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

    public static final String RESOURCE_ID = "RESOURCE_ID";

    @Autowired
    ResourceService resourceService;

    @Autowired
    SongService songService;

    @Autowired
    RMQPublisherService publisher;
    @Autowired
    ResourceProcessorService resourceProcessorService;

    @Bean
    public Consumer<ResourceDto> sink(){
        return value ->  {
            log.info("resource processor starts to process resource with id {}", value.getId());
            ResourceDto resourceDto = resourceService.getResource(value);
            MetadataDto metadata = resourceProcessorService.getMetadata(resourceDto.getMp3data());
            metadata.getMetadata().put(RESOURCE_ID, resourceDto.getId());
            String postedResourceId = songService.postMetadata(metadata);
            log.info("resource with id {} posted", postedResourceId);
            publisher.publishChangingEvent(value);
        };

    }
}
