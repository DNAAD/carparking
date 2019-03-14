package com.coalvalue.repository;


import com.coalvalue.domain.entity.User;
import com.coalvalue.domain.entity.WxTemporaryQrcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface WxTemporaryQrcodeRepository extends JpaRepository<WxTemporaryQrcode, Integer> {




    WxTemporaryQrcode findByScanIdAndAppId(Integer scanId, String corpid);

    Optional<WxTemporaryQrcode> findById(Integer id);



    Page<WxTemporaryQrcode> findByContentAndAppId(String verificationCode, String corpid, Pageable pageable);



}
