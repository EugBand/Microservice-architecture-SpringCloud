package com.epam.epmcacm.msademo.songsrv.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;
import java.util.Map;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongMetadata {

    @Id
    private String id;

    @Column(name = "created_at")
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Instant updatedAt;

    @Column(name = "metadata")
    @ElementCollection
    private Map<String,String> metadata;



}
