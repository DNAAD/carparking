package com.coalvalue.repository;


import com.coalvalue.domain.entity.PlateRecognition;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface PlateRecognitionRepository extends JpaRepository<PlateRecognition, Integer> {




    PlateRecognition findByLicense(String plateNumber);


}
