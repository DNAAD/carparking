package com.coalvalue.repository;


import com.coalvalue.domain.entity.WxPermanentQrcode;
import com.coalvalue.domain.entity.WxTemporaryQrcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface WxPermanentQrcodeRepository extends JpaRepository<WxPermanentQrcode, Integer> {


    WxPermanentQrcode findByItemIdAndItemTypeAndTypeAndAppId(Integer id, String text, String wxQrcodeTypeScatteredOrder, String corpid);
}
