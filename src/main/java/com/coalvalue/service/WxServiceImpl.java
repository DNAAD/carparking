package com.coalvalue.service;

import com.coalvalue.configuration.CommonConstant;

import com.coalvalue.configuration.Constants;
import com.coalvalue.domain.TemporaryQrcode;
import com.coalvalue.domain.entity.*;

import com.coalvalue.repository.TemporaryQrcodeRepository;
import com.coalvalue.repository.WxTemporaryQrcodeRepository;
import com.coalvalue.util.SequenceGenerator;


import com.service.BaseServiceImpl;
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
    public WxTemporaryQrcode getVariableByChannel(String channel, String wxQrcodeTypeWorkbenchConfiguration,Integer expireSeconds,Integer tryCount) {


        if(tryCount == 0){
            return null;
        }


        logger.debug("we are after find wxgeneral ");
        WxTemporaryQrcode wxScanGeneral = wxTemporaryQrcodeRepository.findByChannelAndTypeAndAppIdAndStatus(channel, wxQrcodeTypeWorkbenchConfiguration, Constants.APP_ID, CommonConstant.QRCODE_STATUS_Valid);

        if(wxScanGeneral == null){
            wxScanGeneral = new WxTemporaryQrcode();
            wxScanGeneral.setType(wxQrcodeTypeWorkbenchConfiguration);
            Integer scanId = sequenceGenerator.getScanId();

            wxScanGeneral.setStatus(CommonConstant.QRCODE_STATUS_Valid);

            wxScanGeneral.setExpireSeconds(expireSeconds);

            wxScanGeneral =  wxTemporaryQrcodeRepository.save(wxScanGeneral);
        }



/*        String accessToken = accessTokenManagerService.getAccessToken().getAccessToken();
        WeixinQRCode ticket = AdvancedUtil.createTemporaryQRCode(accessToken, expireSeconds, wxScanGeneral.getScanId());
        if(ticket.getErrorCode() ==  null ){

            Configuration configuration = configurationService.getConfiguration(ConfigurationServiceImpl.path_qrcode_image_resoure_path);
            String filePathToGraphsDir = configuration.getStringValue();//servletContext.getRealPath("/files");

            System.out.println(servletContext.getContextPath());

            logger.debug("filePathToGraphsDir:{} ", filePathToGraphsDir);
            String path = AdvancedUtil.getQRCode(ticket.getTicket(),filePathToGraphsDir+"/company_qrcode_files" + File.separator);

            System.out.println("---------------绝对地址： "+ (new File("/").getAbsolutePath()));
            System.out.println("---------------FileUtil.BASE_DIR： "+ FileUtil.BASE_DIR);
            System.out.println("---------------FileUtil.getWebAppDir： "+ FileUtil.getWebAppDir());

            if(path != null){
                wxScanGeneral.setQrCode(path.replace(filePathToGraphsDir,""));
                wxScanGeneral.setContent(ticket.getUrl());
                wxScanGeneral.setTicket(ticket.getTicket());
                wxScanGeneral.setExpireSeconds(expireSeconds);
                return wxTemporaryQrcodeRepository.save(wxScanGeneral);

            }else {
                return null;
            }
        }else {
           // accessTokenManagerService.updateAccessToken(ticket.getErrorCode());
            return getVariableByChannel(channel, wxQrcodeTypeWorkbenchConfiguration, expireSeconds, tryCount - 1);

        }*/


        return null;//
    }


    @Override
    public WxTemporaryQrcode getById(Integer id) {
        return wxTemporaryQrcodeRepository.findById(id);
    }



    private void save(WxTemporaryQrcode wxTemporaryQrcode) {
        wxTemporaryQrcodeRepository.save(wxTemporaryQrcode);
    }


    public WxTemporaryQrcode getTempByObjectId_with_valid(Integer id, String wxQrcodeTypeScatteredOrder,int expireSeconds, int tryCount) {

        if(tryCount == 0){
            return null;
        }
        WxTemporaryQrcode wxScanGeneral =  wxTemporaryQrcodeRepository.findByCompanyIdAndTypeAndAppIdAndStatus(id, wxQrcodeTypeScatteredOrder, Constants.APP_ID, CommonConstant.QRCODE_STATUS_Valid);



        logger.debug("we are after find WxTemporaryQrcode with valid ");

        if(wxScanGeneral != null && wxScanGeneral.getQrCode() != null && wxScanGeneral.getStatus().equals(CommonConstant.QRCODE_STATUS_Valid)){

            return wxScanGeneral;
        }else {

            if(wxScanGeneral == null ){
                wxScanGeneral = new WxTemporaryQrcode();
                wxScanGeneral.setCompanyId(id);
                wxScanGeneral.setType(wxQrcodeTypeScatteredOrder);
                Integer scanId = sequenceGenerator.getScanId();
                wxScanGeneral.setScanId(scanId);

                wxScanGeneral.setItemType(wxQrcodeTypeScatteredOrder);
                wxScanGeneral.setItemId(id);
                wxScanGeneral.setStatus(CommonConstant.QRCODE_STATUS_Valid);
                wxScanGeneral.setAppId(Constants.APP_ID);
                wxScanGeneral.setExpireSeconds(expireSeconds);

                wxScanGeneral =  wxTemporaryQrcodeRepository.save(wxScanGeneral);
            }

            if(wxScanGeneral.getQrCode() == null ){


         /*       //这边以下 就是 没有获得二维 码的 情况.
                String accessToken = accessTokenManagerService.getAccessToken().getAccessToken();
                WeixinQRCode ticket = AdvancedUtil.createTemporaryQRCode(accessToken, expireSeconds, wxScanGeneral.getScanId());
                if(ticket.getErrorCode() ==  null ){



                    Configuration configuration = configurationService.getConfiguration(ConfigurationServiceImpl.path_qrcode_image_resoure_path);
                    String filePathToGraphsDir = configuration.getStringValue();

                    System.out.println(servletContext.getContextPath());

                    logger.debug("filePathToGraphsDir:{} ", filePathToGraphsDir);
                    String path = AdvancedUtil.getQRCode(ticket.getTicket(),filePathToGraphsDir+"/company_qrcode_files" + File.separator);

                    System.out.println("---------------绝对地址： "+ (new File("/").getAbsolutePath()));
                    System.out.println("---------------FileUtil.BASE_DIR： "+ FileUtil.BASE_DIR);
                    System.out.println("---------------FileUtil.getWebAppDir： "+ FileUtil.getWebAppDir());

                    if(path != null){
                        wxScanGeneral.setQrCode(path.replace(filePathToGraphsDir,""));
                        wxScanGeneral.setContent(ticket.getUrl());
                        wxScanGeneral.setTicket(ticket.getTicket());
                        wxScanGeneral.setExpireSeconds(expireSeconds);
                        return wxTemporaryQrcodeRepository.save(wxScanGeneral);


                    }else {
                        return null;
                    }
                }else {
                    accessTokenManagerService.updateAccessToken(ticket.getErrorCode());
                    return getTempByObjectId(id,  wxQrcodeTypeScatteredOrder, expireSeconds,tryCount -1 );

                }*/

            }

        }


        return wxScanGeneral;


    }
    public WxTemporaryQrcode getTempByObjectId(Integer id, String wxQrcodeTypeScatteredOrder,int expireSeconds, int tryCount) {

        if(tryCount == 0){
            return null;
        }
        WxTemporaryQrcode wxScanGeneral =  wxTemporaryQrcodeRepository.findByCompanyIdAndTypeAndAppIdAndStatus(id, wxQrcodeTypeScatteredOrder, Constants.APP_ID, CommonConstant.QRCODE_STATUS_Valid);



        logger.debug("we are after find wxgeneral ");

        if(wxScanGeneral != null && wxScanGeneral.getQrCode() != null ){

            return wxScanGeneral;
        }else {

            if(wxScanGeneral == null ){
                wxScanGeneral = new WxTemporaryQrcode();
                wxScanGeneral.setCompanyId(id);
                wxScanGeneral.setType(wxQrcodeTypeScatteredOrder);
                Integer scanId = sequenceGenerator.getScanId();
                wxScanGeneral.setScanId(scanId);

                wxScanGeneral.setItemType(wxQrcodeTypeScatteredOrder);
                wxScanGeneral.setItemId(id);
                wxScanGeneral.setStatus(CommonConstant.QRCODE_STATUS_Valid);
                wxScanGeneral.setAppId(Constants.APP_ID);
                wxScanGeneral.setExpireSeconds(expireSeconds);
                //  if(wxQrcodeTypeScatteredOrder.equals(Constants.WX_QRCODE_TYPE_COMPANY)){
                //      wxScanGeneral.setSubScene(WeixinSubSceneEnum.COMPANY_SCAN_MAIN_AREA.getId());
                //  }
                //wxScanGeneral = saveTemporary(wxScanGeneral);
                wxScanGeneral =  wxTemporaryQrcodeRepository.save(wxScanGeneral);
            }

            if(wxScanGeneral.getQrCode() == null ){

/*

                //这边以下 就是 没有获得二维 码的 情况.
                String accessToken = accessTokenManagerService.getAccessToken().getAccessToken();
                WeixinQRCode ticket = AdvancedUtil.createTemporaryQRCode(accessToken, expireSeconds, wxScanGeneral.getScanId());
                if(ticket.getErrorCode() ==  null ){



                    Configuration configuration = configurationService.getConfiguration(ConfigurationServiceImpl.path_qrcode_image_resoure_path);
                    String filePathToGraphsDir = configuration.getStringValue();//servletContext.getRealPath("/files");

                    //  String filePathToGraphsDir = servletContext.getRealPath("/files");
                    System.out.println(servletContext.getContextPath());

                    logger.debug("filePathToGraphsDir:{} ", filePathToGraphsDir);
                    String path = AdvancedUtil.getQRCode(ticket.getTicket(),filePathToGraphsDir+"/company_qrcode_files" + File.separator);

                    System.out.println("---------------绝对地址： "+ (new File("/").getAbsolutePath()));
                    System.out.println("---------------FileUtil.BASE_DIR： "+ FileUtil.BASE_DIR);
                    System.out.println("---------------FileUtil.getWebAppDir： "+ FileUtil.getWebAppDir());
                    // System.out.println("---------------根目录所对应的绝对路径： "+ request.getServletPath());

                    //   wxScanGeneral.setScanId(scanId);
                    if(path != null){
                        wxScanGeneral.setQrCode(path.replace(filePathToGraphsDir,""));
                        wxScanGeneral.setContent(ticket.getUrl());
                        wxScanGeneral.setTicket(ticket.getTicket());
                        wxScanGeneral.setExpireSeconds(expireSeconds);
                        //return saveTemporary(wxScanGeneral);
                        return wxTemporaryQrcodeRepository.save(wxScanGeneral);


                    }else {
                        return null;
                    }
                }else {
                    accessTokenManagerService.updateAccessToken(ticket.getErrorCode());
                    return getTempByObjectId(id,  wxQrcodeTypeScatteredOrder, expireSeconds,tryCount -1 );
                    //logger.debug("===无法获得 更新了 accessTocken");
                    // return null;
                }
*/

            }

        }


        return wxScanGeneral;



    }



    @Transactional
    private WxTemporaryQrcode saveTemporary(WxTemporaryQrcode wxScanGeneral) {

        return wxTemporaryQrcodeRepository.save(wxScanGeneral);

    }














}
