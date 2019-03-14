package com.coalvalue.repository;


import com.coalvalue.domain.entity.QualityInspectionReport;
import com.coalvalue.domain.entity.TemporaryQrcode;
import com.coalvalue.domain.entity.TransportOperation;
import com.coalvalue.domain.entity.WxTemporaryQrcode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface TemporaryQrcodeRepository extends JpaRepository<TemporaryQrcode, Integer> {


    Optional<TemporaryQrcode> findById(Integer fromTransport);



    TemporaryQrcode findByUniqueId(String u);


}
