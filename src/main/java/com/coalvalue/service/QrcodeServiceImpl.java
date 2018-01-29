package com.coalvalue.service;


import com.alibaba.fastjson.JSON;
import com.coalvalue.configuration.Constants;
import com.coalvalue.domain.Distributor;
import com.coalvalue.domain.TemporaryQrcode;
import com.coalvalue.domain.entity.Behavioural;
import com.coalvalue.enumType.DataSynchronizationTypeEnum;
import com.coalvalue.enumType.ResourceType;
import com.coalvalue.notification.NotificationData;
import com.coalvalue.repository.BehaviouralRepository;
import com.coalvalue.repository.PlateRecognitionRepository;
import com.coalvalue.repository.TemporaryQrcodeRepository;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.service.BaseServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.lang.invoke.ConstantCallSite;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by silence yuan on 2015/7/25.
 */

@Service("qrcodeService")
public class QrcodeServiceImpl extends BaseServiceImpl implements QrcodeService {

    @Autowired
    private BehaviouralRepository behaviouralRepository;

    @Autowired
    private BehaviouralService behaviouralService;

    @Autowired
    private TemporaryQrcodeRepository temporaryQrcodeRepository;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private MqttService mqttService;



    @Override
    @Transactional
    public void create(NotificationData data) {

        Behavioural behavioural = new Behavioural();
        behavioural = behaviouralRepository.save(behavioural);

    }

  // @PostConstruct
   @Override
    public void analyis(String text) {

        System.out.println("------qrcode ---------------text---" + text);

        try{
            behaviouralService.analyisQrcode(text);
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    @Override
    public Page<Map> querySynthesized(Object o, Pageable pageable) {


        return new PageImpl<Map>(maps,pageable,maps.size());
    }

    @Override

    @Transactional
    public TemporaryQrcode getBindQrcodeForDistributor(Distributor distributor) {


        TemporaryQrcode temporaryQrcode = new TemporaryQrcode();
        temporaryQrcode.setStatus("");
        temporaryQrcode.setUniqueId(RandomStringUtils.randomAlphanumeric(30).toUpperCase());
        temporaryQrcode.setItemId(distributor.getId());
        temporaryQrcode.setItemType(ResourceType.Distributor.getText());




        Map map = new HashMap<>();




        map.put("uniqueId",temporaryQrcode.getUniqueId());
        map.put("type", DataSynchronizationTypeEnum.Qrcode.getText());
        map.put("appId", configurationService.getAppId());

        map.put("appSecret",configurationService.getAppSecret());
        String msg = JSON.toJSONString(map);


        logger.debug("===================, {}", map.toString());
        mqttService.publishToHost(msg);
        temporaryQrcode =  temporaryQrcodeRepository.save(temporaryQrcode);

        return temporaryQrcode;
    }


    List<Map> maps = new ArrayList<>();







    @Override
    @Transactional
    public TemporaryQrcode upDateBindQrcodeForDistributor(String u, Map map) {

        TemporaryQrcode temporaryQrcode = temporaryQrcodeRepository.findByUniqueId(u);
        if(temporaryQrcode != null){


            String qrcode =(String) map.get("qrcode");
            temporaryQrcode.setContent(qrcode);




            temporaryQrcode =  temporaryQrcodeRepository.save(temporaryQrcode);

            return temporaryQrcode;
        }
        return null;

    }













    private static final long serialVersionUID = 6441489157408381878L;

    private Executor executor = Executors.newSingleThreadExecutor();

    private Webcam webcam = null;
    private WebcamPanel panel = null;
   // private JTextArea textarea = null;
/*    static {
        Webcam.setDriver(new IpCamDriver());
    }*/

  //  @PostConstruct
    public void WebcamQRCodeExample() {



        Dimension size = WebcamResolution.QVGA.getSize();
       // IpCamDeviceRegistry.register(new IpCamDevice("Lignano", "http://88.37.116.138/mjpg/video.mjpg", IpCamMode.PUSH));
        webcam = Webcam.getWebcams().get(1);
       // webcam =Webcam.getDefault();// Webcam.getWebcams().get(0);
     //   webcam.setViewSize(size);







        executor.execute(new Runnable() {
                @Override
                public void run() {
                    do {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Result result = null;
                        BufferedImage image = null;

                        if (webcam.isOpen()) {

                            if ((image = webcam.getImage()) == null) {
                                continue;
                            }

                            LuminanceSource source = new BufferedImageLuminanceSource(image);
                            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                            try {
                                result = new MultiFormatReader().decode(bitmap);
                            } catch (NotFoundException e) {
                                // fall thru, it means there is no QR code in image
                            }
                        }

                        if (result != null) {
                            System.out.println("------qrcode ------------------" + result.getText());
                        }
                     //   System.out.println("------qrcode ------------------ waiting " );
                    } while (true);
                }
            });

  /*      public static void main(String[] args) throws MalformedURLException {
            JFrame f = new JFrame("Live Views From Lignano Beach");
            f.add(new WebcamPanel(Webcam.getDefault()));
            f.pack();
            f.setVisible(true);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }*/










/*

        panel = new WebcamPanel(webcam);
        panel.setPreferredSize(size);
        panel.setFPSDisplayed(true);

        textarea = new JTextArea();
        textarea.setEditable(false);
        textarea.setPreferredSize(size);

        add(panel);
        add(textarea);

        pack();
        setVisible(true);

        executor.execute(this);*/
    }



/*
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "example-runner");
        t.setDaemon(true);
        return t;
    }
*/



}
