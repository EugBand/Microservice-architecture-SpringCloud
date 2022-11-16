package com.epam.epmcacm.msademo.resourcesrv.service;

import com.epam.epmcacm.msademo.resourcesrv.dto.ResourceDto;
import com.epam.epmcacm.msademo.resourcesrv.dto.StorageDto;
import com.epam.epmcacm.msademo.resourcesrv.dto.StorageType;
import com.epam.epmcacm.msademo.resourcesrv.entity.Resource;
import com.epam.epmcacm.msademo.resourcesrv.exception.BadRequestException;
import com.epam.epmcacm.msademo.resourcesrv.exception.FileProcessingException;
import com.epam.epmcacm.msademo.resourcesrv.mapper.ResourceMapper;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Book;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.amazonaws.services.mediaconvert.model.AudioCodec.MP3;

@Service
@Slf4j
public class ResourceService {

    public static final String ERROR_UPLOADING_MP3_FILE = "Error uploading mp3 file: ";

    public static final String ERROR_DOWNLOADING_MP_3_FILE = "Error downloading mp3 file: ";

    public static final String ERROR_RETRIEVINGMP_3_FILE_FOR_ID = "Error retrieving mp3 file for id:";

    public static final String MISMATCH_COUNT = "Mismatch count of resources: %s and mp3 deleted files:";

    public static final String IS_NOT_OF_MP_3_FORMAT = "Provided file is not of mp3 format";

    @Autowired DBService dbService;

    @Autowired S3Service s3FileService;

    @Autowired RMQPublisherService publisher;

    @Autowired ResourceMapper mapper;

    @Autowired StorageService storageService;

    public ResourceDto createResource(MultipartFile multipartFile) {
        if(!validateIfMp3File(multipartFile)) {
            throw new BadRequestException(IS_NOT_OF_MP_3_FORMAT);
        }

        StorageDto stagingStorageDto = storageService.fetchStorageByType(StorageType.STAGING);
        String id = UUID.randomUUID().toString();
        String storageId = stagingStorageDto.getId();
        dbService.addResourceData(id, storageId);
        String resourceId;
        String bucketName = stagingStorageDto.getBucketName();
        String path = stagingStorageDto.getPath();
        try {
            resourceId = s3FileService.upLoadFile(multipartFile, id, bucketName, path);
        } catch (IOException e) {
            log.error(ERROR_UPLOADING_MP3_FILE + e.getMessage());
            deleteResources(List.of(id));
            throw new FileProcessingException(ERROR_UPLOADING_MP3_FILE + e.getMessage(), e);
        }
        ResourceDto resourceDto = ResourceDto.builder()
                .id(resourceId)
                .storageId(storageId)
                .build();
        publisher.publishCreationEvent(resourceDto);
        log.info("resource created with path: {}", resourceId);
        return resourceDto;
    }

    public ResourceDto getResource(String id) {
        Resource resource = dbService.getResourceData(id);
        String storageId = resource.getStorageId();
        StorageDto storageDto = storageService.fetchStorageByStorageId(storageId);
        ResourceDto resourceDto = mapper.toDto(resource);
        String resourceId = resource.getId();
        String bucketName = storageDto.getBucketName();
        String path = storageDto.getPath();
        resourceDto.setMp3data(s3FileService.downLoadFile(resourceId, bucketName, path));
        log.info("Get resource for id: {}", id);
        return resourceDto;
    }

    public List<String> deleteResources(List<String> ids) {
        log.info("Resources metadata deleted for {} records", ids.size());
        List<StorageDto> storageDtos = storageService.getStorages();
        Map<String, StorageDto> resourceMap = ids.stream().collect(Collectors.toMap(Function.identity(),
                id -> storageDtos.stream()
                    .filter(s -> s.getId().equals(dbService.getResourceData(id).getStorageId()))
                    .findFirst()
                    .orElseThrow(() -> new BadRequestException("Wrond storage id"))));
        List<String> deletedDbResources = dbService.deleteResources(ids);
        resourceMap.forEach((id, storage) -> s3FileService.deleteFile(id, storage.getBucketName(), storage.getPath()));
        return ids;
    }

    private boolean validateIfMp3File(final MultipartFile file) {
        return MP3.toString().equalsIgnoreCase(FilenameUtils.getExtension(file.getResource().getFilename()));
    }
}
