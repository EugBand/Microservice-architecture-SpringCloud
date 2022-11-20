package com.epam.epmcacm.msademo.storagesrv.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class StorageDto {

    @Builder.Default
    private String id = "";
    private StorageType storageType;
    private String bucketName;
    private String path;
}
