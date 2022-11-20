package com.epam.epmcacm.msademo.resourcesrv.service;

import com.epam.epmcacm.msademo.resourcesrv.entity.Resource;
import com.epam.epmcacm.msademo.resourcesrv.exception.BadRequestException;
import com.epam.epmcacm.msademo.resourcesrv.repository.ResourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DBService {

    public static final String NOT_FOUND_IN_BD = "Resource with id %s not found in bd";

    @Autowired ResourceRepository resourceRepository;

    public String addResourceData(String id, String storageId) {
        log.info("Start adding resource to DB with id {}", id);
        Resource resource = Resource.builder()
                .id(id)
//                .filePath(fileName)
                .createdAt(Instant.now())
                .storageId(storageId)
                .build();
        Resource savedResource = resourceRepository.save(resource);
        String savedResourceId = savedResource.getId();
        log.info("resource added to DB with id: {}", id);
        return savedResourceId;
    }

    public Resource getResourceData(String id) {
        log.info("Start getting resource to DB with id {}", id);
        Optional<Resource> resource = resourceRepository.findById(id);
        Resource savedResource = resource.orElseThrow(() -> new BadRequestException(String.format(NOT_FOUND_IN_BD, id)));
        log.info("Get resource from DB with id: {}", id);
        return savedResource;
    }

    public List<String> deleteResources(List<String> ids) {
        log.info("Start deleting resources from DB quantity {}", ids.size());
        resourceRepository.deleteAllById(ids);
        log.info("Resources metadata deleted for {} records", ids.size());
        return ids;
    }
}
