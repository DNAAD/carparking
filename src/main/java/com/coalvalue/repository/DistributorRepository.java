package com.coalvalue.repository;


import com.coalvalue.domain.entity.Distributor;

import com.coalvalue.domain.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface DistributorRepository extends JpaRepository<Distributor, Integer> {




    Optional<Distributor> findById(Integer index);



    Distributor getByName(String name);


    List<String> findUuidBy();

    Distributor findByUuid(String uuid);

    List<Distributor> findByModifyDateAfter(LocalDateTime lastSync);

    Distributor findByNo(String index);

    List<Distributor> findByUuidIn(List<String> uuids);
}
