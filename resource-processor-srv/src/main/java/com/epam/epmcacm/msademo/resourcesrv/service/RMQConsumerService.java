package com.epam.epmcacm.msademo.resourcesrv.service;

import com.epam.epmcacm.msademo.resourcesrv.dto.MetadataDto;
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

    @Autowired
    ResourceService resourceService;

    @Autowired
    SongService songService;
    @Autowired
    ResourceProcessorService resourceProcessorService;

    @Bean
    public Consumer<String> sink(){
        return value ->  {
            ResourceDto resourceDto = resourceService.getResource(value);
            MetadataDto metadata = resourceProcessorService.getMetadata(resourceDto.getMp3data());
            String postedResourceId = songService.postMetadata(metadata);
            log.info("resource with id {} posted", postedResourceId);
        };

    }
}
