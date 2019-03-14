package com.coalvalue.repository;

import com.coalvalue.domain.entity.Company;
import com.coalvalue.domain.entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Blob;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by zhao yuan on 01/10/2015.
 */

public interface ConfigurationRepository extends JpaRepository<Configuration, Integer> {


    Optional<Configuration> findById(Integer id);





    Configuration findByUuid(String uuid);

    List<Configuration> findByUuidIn(List<String> uuids);

    Configuration findByKey(String imageStrategy);


}
