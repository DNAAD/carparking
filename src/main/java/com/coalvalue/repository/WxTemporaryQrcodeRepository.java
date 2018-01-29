package com.coalvalue.repository;


import com.coalvalue.domain.TemporaryQrcode;
import com.coalvalue.domain.entity.WxTemporaryQrcode;
import com.coalvalue.repository.base.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface WxTemporaryQrcodeRepository extends BaseJpaRepository<WxTemporaryQrcode, Integer> {




    WxTemporaryQrcode findByScanIdAndAppId(Integer scanId, String corpid);

    WxTemporaryQrcode findById(Integer id);



    WxTemporaryQrcode findByCompanyIdAndTypeAndAppIdAndStatus(Integer id, String wxQrcodeTypeScatteredOrder, String corpid, String qrcode_status_invalid);

    Page<WxTemporaryQrcode> findByContentAndAppId(String verificationCode, String corpid, Pageable pageable);

    WxTemporaryQrcode findByTicket(String verificationCode);

    WxTemporaryQrcode findByChannelAndTypeAndAppIdAndStatus(String channel, String wxQrcodeTypeWorkbenchConfiguration, String corpid, String qrcode_status_valid);



}
