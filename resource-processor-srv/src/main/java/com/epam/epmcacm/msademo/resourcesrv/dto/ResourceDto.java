package com.epam.epmcacm.msademo.resourcesrv.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class ResourceDto {
//    private String id;
//
//    private Instant createdAt;
//
//    private Instant updatedAt;
//
//    private String filePath;
//
//    private byte[] mp3data;
//}

@Data
@Builder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDto {

    private String id;
    private String storageId;
    @Builder.Default
    private byte[] mp3data = new byte[]{};
}

