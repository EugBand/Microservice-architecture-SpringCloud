package com.epam.epmcacm.msademo.resourcesrv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDto {
    private String id;

    private Instant createdAt;

    private Instant updatedAt;

    private String filePath;

    private byte[] mp3data;
}
