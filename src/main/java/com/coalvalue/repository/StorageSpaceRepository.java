package com.coalvalue.repository;

import com.coalvalue.domain.entity.Reconciliation;
import com.coalvalue.domain.entity.StorageSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import io.grpc.Context;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface StorageSpaceRepository extends JpaRepository<StorageSpace, Integer> {



    Page<StorageSpace> findById(Integer associatedStorageId, Pageable pageable);



    Optional<StorageSpace> findById(Integer storageId);


    StorageSpace findByNo(String no);



    StorageSpace findByUuid(String objectUuid);

    List<StorageSpace> findByUuidIn(List<String> uuids);
}
