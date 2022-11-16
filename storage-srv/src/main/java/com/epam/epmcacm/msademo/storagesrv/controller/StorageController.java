package com.epam.epmcacm.msademo.storagesrv.controller;

import com.epam.epmcacm.msademo.storagesrv.Dto.StorageDto;
import com.epam.epmcacm.msademo.storagesrv.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/api/v1/storages", produces = MediaType.APPLICATION_JSON_VALUE)
public class StorageController {

    @Autowired StorageService service;

    @PostMapping
    public String addStorage(@RequestBody StorageDto storageDto){
        log.info("Create new storage");
        return service.createStorage(storageDto);
    }

    @GetMapping
    public List<StorageDto> getStorages(){
        return service.fetchStorages();
    }

    @DeleteMapping
    public List<String> deleteStorages(@RequestParam List<String> ids){
        return service.deleteStorages(ids);
    }

}
