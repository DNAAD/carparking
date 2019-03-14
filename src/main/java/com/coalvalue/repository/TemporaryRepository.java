package com.coalvalue.repository;


import com.coalvalue.domain.entity.TemporaryQrcode;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface TemporaryRepository extends JpaRepository<TemporaryQrcode, Integer> {

}
