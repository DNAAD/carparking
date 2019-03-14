package com.coalvalue.repository;


import com.coalvalue.domain.entity.Preference;

import org.springframework.data.jpa.repository.JpaRepository;

;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface PreferenceRepository extends JpaRepository<Preference, Integer> {


    Preference findByEmployeeUuid(String uuid);


}
