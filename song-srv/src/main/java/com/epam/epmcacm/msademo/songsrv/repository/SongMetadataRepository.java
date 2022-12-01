package com.epam.epmcacm.msademo.songsrv.repository;

import com.epam.epmcacm.msademo.songsrv.entity.SongMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongMetadataRepository extends JpaRepository<SongMetadata, String> {
}
