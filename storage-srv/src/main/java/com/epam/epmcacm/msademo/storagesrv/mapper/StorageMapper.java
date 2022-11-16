package com.epam.epmcacm.msademo.storagesrv.mapper;

import com.epam.epmcacm.msademo.storagesrv.Dto.StorageDto;
import com.epam.epmcacm.msademo.storagesrv.entity.Storage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
//@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StorageMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "bucketName", target = "bucketName")
    @Mapping(source = "storageType", target = "storageType")
    @Mapping(source = "path", target = "path")
    StorageDto toDto(Storage storage);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "bucketName", target = "bucketName")
    @Mapping(source = "storageType", target = "storageType")
    @Mapping(source = "path", target = "path")
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Storage toEntity(StorageDto storageDto);

}
