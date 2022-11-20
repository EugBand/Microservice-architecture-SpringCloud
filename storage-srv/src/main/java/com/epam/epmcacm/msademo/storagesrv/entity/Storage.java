package com.epam.epmcacm.msademo.storagesrv.entity;

import com.epam.epmcacm.msademo.storagesrv.Dto.StorageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Storage {

    @Id
    private String id;
    @Column(name = "created_at")
    @CreatedDate
    private Instant createdAt;
    @Column(name = "updated_at")
    @LastModifiedDate
    private Instant updatedAt;
    private StorageType storageType;
    private String bucketName;
    private String path;
}
