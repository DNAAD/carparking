package com.coalvalue.repository;


import com.coalvalue.domain.entity.Camera;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface CameraRepository extends JpaRepository<Camera, Integer> {


    List<Camera> findByType(String text);

    Optional<Camera> findById(Integer index);

    Page<Camera> findByType(String text, Pageable pageable);

}
