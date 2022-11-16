package com.epam.epmcacm.msademo.resourcesrv.dto;

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
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class StorageDto {

    @Builder.Default
    String id = "";
    @Builder.Default
    StorageType storageType = null;
    @Builder.Default
    String bucketName  = "";
    @Builder.Default
    String path = "";
}
