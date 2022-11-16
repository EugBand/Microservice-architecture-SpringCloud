package com.epam.epmcacm.msademo.storagesrv.repository;

import com.epam.epmcacm.msademo.storagesrv.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRepository extends JpaRepository<Storage, String>{
}
