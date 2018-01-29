package com.coalvalue.service;

import com.coalvalue.domain.Distributor;
import com.coalvalue.domain.TemporaryQrcode;
import com.coalvalue.domain.entity.PlateRecognition;
import com.coalvalue.notification.NotificationData;
import com.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
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
}
