package com.epam.epmcacm.msademo.resourcesrv.mapper;

import com.epam.epmcacm.msademo.resourcesrv.dto.ResourceDto;
import com.epam.epmcacm.msademo.resourcesrv.entity.Resource;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ResourceMapper {

    ResourceDto toDto(Resource resource);

    Resource toModel(ResourceDto resourceDto);

}
