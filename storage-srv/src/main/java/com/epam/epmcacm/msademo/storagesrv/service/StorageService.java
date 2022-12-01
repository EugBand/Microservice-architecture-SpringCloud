package com.epam.epmcacm.msademo.storagesrv.service;

import com.amazonaws.services.s3.model.Bucket;
import com.epam.epmcacm.msademo.storagesrv.dto.StorageDto;
import com.epam.epmcacm.msademo.storagesrv.entity.Storage;
import com.epam.epmcacm.msademo.storagesrv.exception.BadRequestException;
import com.epam.epmcacm.msademo.storagesrv.mapper.StorageMapper;
import com.epam.epmcacm.msademo.storagesrv.repository.StorageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StorageService {

    public static final String WRONG_BUCKETS_TO_REMOVE = "Can't find any buckets to remove";

    @Autowired private StorageRepository repository;

    @Autowired private S3Service s3Service;

    @Autowired private StorageMapper mapper;

    public String createStorage(StorageDto storageDto) {
        log.info("Start creating storage");
        Storage storage = mapper.toEntity(storageDto);
        String storageId = s3Service.createBucket(storageDto.getBucketName());
        storage.setId(storageId);
        Storage savedStorage = repository.save(storage);
        log.info("Finish creating storages");
        return savedStorage.getId();
    }

    public List<StorageDto> fetchStorages() {
        log.info("Start fetching storages");
        List<Storage> storages = repository.findAll();
        List<Bucket> buckets = s3Service.getBuckets();
        List<String> bucketsName = buckets.stream().map(Bucket::getName).collect(Collectors.toList());
        List<StorageDto> filteredStorages = storages.stream().filter(s -> bucketsName.contains(s.getBucketName()))
                .map(mapper::toDto).collect(Collectors.toList());
        log.info("Finish fetching storages");
        return filteredStorages;
    }

    public List<String> deleteStorages(List<String> ids) {
        log.info("Start deleting storages");
        List<Storage> storages = repository.findAll();
        List<String> bucketNames = storages.stream().map(Storage::getBucketName).collect(Collectors.toList());
        repository.deleteAllById(storages.stream().map(Storage::getId).collect(Collectors.toList()));
        s3Service.deleteBuckets(bucketNames);
        if (storages.isEmpty() && bucketNames.isEmpty()) {
            throw new BadRequestException(WRONG_BUCKETS_TO_REMOVE);
        }
        log.info("Finish deleting storages");
        return ids;
    }
}
