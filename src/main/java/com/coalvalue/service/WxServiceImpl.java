package com.coalvalue.service;

import com.coalvalue.configuration.CommonConstant;

import com.coalvalue.configuration.Constants;
import com.coalvalue.domain.entity.*;

import com.coalvalue.repository.WxTemporaryQrcodeRepository;
import com.coalvalue.util.SequenceGenerator;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;

/**
 * Created by silence yuan on 2015/7/25.
 */

@Service("wxService")
public class WxServiceImpl extends BaseServiceImpl implements WxService {



    @Autowired
    private WxTemporaryQrcodeRepository wxTemporaryQrcodeRepository;


    @Autowired
    private ServletContext servletContext;

  //  @Autowired
 //   private AccessTokenManagerService accessTokenManagerService;


    @Autowired
    private SequenceGenerator sequenceGenerator;



    @Autowired
    private ConfigurationService configurationService;





    @Override
    public WxTemporaryQrcode getTemporaryScan(Integer scanId) {


        return wxTemporaryQrcodeRepository.findByScanIdAndAppId(scanId, Constants.APP_ID);
        // return wxGeneralRepository.findByScanIdAndAppId(scanId,Constants.CORPID);
    }




    @Override
    public WxTemporaryQrcode getById(Integer id) {
        return wxTemporaryQrcodeRepository.findById(id).get();
    }



    private void save(WxTemporaryQrcode wxTemporaryQrcode) {
        wxTemporaryQrcodeRepository.save(wxTemporaryQrcode);
    }





    @Transactional
    private WxTemporaryQrcode saveTemporary(WxTemporaryQrcode wxScanGeneral) {

        return wxTemporaryQrcodeRepository.save(wxScanGeneral);

    }














}
