package com.coalvalue.repository;



import com.coalvalue.domain.entity.Configuration;
import com.coalvalue.domain.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {


    Optional<Equipment> findById(Integer clientId);

    Equipment findByTypeAndName(String text, String deviceId);

    List<Equipment> findByType(String text);


    Equipment findByTypeAndDeviceId(String text, String deviceId);


    List<Equipment> findByUuidIn(List<String> uuids);

}
