package com.epam.epmcacm.msademo.resourcesrv.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tika.metadata.Metadata;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class MetadataDto {

    Map<String, String> metadata;

    public MetadataDto(Metadata metadata) {
      this.metadata = Arrays.stream(metadata.names()).collect(Collectors.toMap(Function.identity(), metadata::get));
    }
}
