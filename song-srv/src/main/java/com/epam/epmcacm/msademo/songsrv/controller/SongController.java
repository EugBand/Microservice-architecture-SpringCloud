package com.epam.epmcacm.msademo.songsrv.controller;

import com.epam.epmcacm.msademo.songsrv.dto.MetadataDto;
import com.epam.epmcacm.msademo.songsrv.service.SongMetadataService;
import com.epam.epmcacm.msademo.songsrv.validation.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/songs", produces = MediaType.APPLICATION_JSON_VALUE)
public class SongController {

    @Autowired SongMetadataService service;

    @PostMapping
    public String addMetadata(HttpServletRequest request, HttpServletResponse response, @RequestBody MetadataDto metadataDto) {
        log.info("Begin to create song metadata");
        return service.createMetadata(metadataDto);
    }

    @GetMapping("/{songId}")
    public MetadataDto getMetadata(
            @PathVariable @UUID String songId) {
        return service.fetchMetadata(songId);
    }

    @DeleteMapping
    public List<String> deleteMetadata(@RequestParam List<String> ids){
        return service.deleteMetadatasByIds(ids);
    }
}

