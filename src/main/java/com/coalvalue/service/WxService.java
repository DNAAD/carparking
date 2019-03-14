package com.coalvalue.service;

import com.coalvalue.domain.entity.*;




/**
 * Created by silence yuan on 2015/7/25.
 */
public interface WxService extends BaseService {



    WxTemporaryQrcode getTemporaryScan(Integer scanId);

    WxTemporaryQrcode getById(Integer id);




}
