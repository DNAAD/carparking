package com.coalvalue.repository;


import com.coalvalue.domain.entity.Loadometer;
import com.coalvalue.domain.entity.LoadometerPlateRecongiseCemera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface LoadometerPlateRecongiseCemeraRepository extends JpaRepository<LoadometerPlateRecongiseCemera, Integer> {


    List<LoadometerPlateRecongiseCemera> findByCemaraNo(String name);
}
