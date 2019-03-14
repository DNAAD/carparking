package com.coalvalue.service;

import com.coalvalue.domain.entity.Distributor;
import com.coalvalue.domain.entity.TemporaryQrcode;
import com.coalvalue.domain.entity.StorageSpace;
import com.coalvalue.domain.entity.WxPermanentQrcode;
import com.coalvalue.notification.NotificationData;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by silence yuan on 2015/7/25.
 */
public interface QrcodeService extends BaseService {



    void create(NotificationData data);

  //  void analyis(String text);



    void analyis(String text);

    Page<Map> querySynthesized(Object o, Pageable pageable);


    TemporaryQrcode getBindQrcodeForDistributor(Distributor distributor);


    @Transactional
    TemporaryQrcode upDateBindQrcodeForDistributor(String u, Map map);

    WxPermanentQrcode createQrcode(StorageSpace storageSpace);


    void saveStorageQrocde(String storageNo, String content);

    WxPermanentQrcode getCompany();


}
