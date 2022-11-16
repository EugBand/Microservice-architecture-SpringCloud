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
    String id = "";
    StorageType storageType;
    String bucketName;
    String path;
}
