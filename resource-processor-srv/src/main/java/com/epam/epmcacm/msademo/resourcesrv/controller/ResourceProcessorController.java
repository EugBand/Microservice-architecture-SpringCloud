package com.epam.epmcacm.msademo.resourcesrv.controller;

import com.epam.epmcacm.msademo.resourcesrv.dto.MetadataDto;
import com.epam.epmcacm.msademo.resourcesrv.service.ResourceProcessorService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/processor", produces = MediaType.APPLICATION_JSON_VALUE)
public class ResourceProcessorController {

    @Autowired ResourceProcessorService service;

    @GetMapping
    public MetadataDto getMetadata(@RequestParam("file") MultipartFile file) {
        return service.getMetadata(file);
    }
}
