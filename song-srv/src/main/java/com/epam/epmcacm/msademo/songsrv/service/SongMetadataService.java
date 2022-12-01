package com.epam.epmcacm.msademo.songsrv.service;

import com.epam.epmcacm.msademo.songsrv.dto.MetadataDto;
import com.epam.epmcacm.msademo.songsrv.entity.SongMetadata;
import com.epam.epmcacm.msademo.songsrv.exception.NotFoundException;
import com.epam.epmcacm.msademo.songsrv.repository.SongMetadataRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class SongMetadataService {

    @Autowired
    SongMetadataRepository songMetadataRepository;

    public static final String RESOURCE_ID = "RESOURCE_ID";
    public String createMetadata(MetadataDto metadataDto){
        log.info("Start creating metadata");
        Map<String, String> metadataMap = metadataDto.getMetadata();
        String id = metadataMap.containsKey(RESOURCE_ID) ? metadataMap.get(RESOURCE_ID) : UUID.randomUUID().toString();
        SongMetadata songMetadata = SongMetadata.builder()
                .metadata(metadataDto.getMetadata())
                .id(id)
                .createdAt(Instant.now())
                .build();
        String savedId = songMetadataRepository.save(songMetadata).getId();
        log.info("Song metadata created with id: {}", savedId);
        return savedId;
    }

    public MetadataDto fetchMetadata(String id){
        log.info("Start fetching metadata");
        Optional<SongMetadata> songMetadataOptional = songMetadataRepository.findById(id);
        log.info("Get song metadata for id: {}", id);
        SongMetadata songMetadata = songMetadataOptional
                .orElseThrow(() -> new NotFoundException(String.format("Error retrieving metadata with id: %s", id)));
        return new MetadataDto(songMetadata.getMetadata());
    }

    public List<String> deleteMetadatasByIds(List<String> ids){
        log.info("Start deleting metadata");
        songMetadataRepository.deleteAllById(ids);
        log.info("Songs metadata deleted for {} records", ids.size());
        return ids;
    }
}
