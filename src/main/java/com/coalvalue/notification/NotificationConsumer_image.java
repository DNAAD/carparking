package com.coalvalue.notification;


import com.coalvalue.configuration.MqttPublishSample;
import com.coalvalue.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.bus.Event;
import reactor.fn.Consumer;

@Service
public class NotificationConsumer_image implements Consumer<Event<NotificationData_image>> {
 



    RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private StorageService storageService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    public static String topic__COALPIT_DELIVERY_workbench = "/topic/COALPIT_DELIVERY_workbench/1";
    public static String topic__COALPIT_DELIVERY_report = "/topic/COALPIT_DELIVERY_report/1";


    @Autowired
    private MqttPublishSample mqttPublishSample;


/*
    @Autowired
    private CameraService cameraService;
*/


    @Autowired
    private ConfigurationService configurationService;




    @Override
    public void accept(Event<NotificationData_image> notificationDataEvent) {


        NotificationData_image notificationData = notificationDataEvent.getData();

/*

        if(notificationDataEvent.getKey().equals(ReactorEventConfig.notificationConsumer_image_create_event)){



          //  Camera deliveryOrder = cameraService.getById(2);

         *//*   CameraObject deliveryOrder = cameraService.getDefault();
            BufferedImage bufferedImage = deliveryOrder.getBufferedImage();





            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ImageIO.write(bufferedImage, "png", baos);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] res=baos.toByteArray();

*//*
            System.out.println("==========================notificationConsumer_configuration_event={}");




            String imageString ="";// Base64.getDecoder().decode(res);

            Map map = new HashMap<>();
            map.put("type", DataSynchronizationTypeEnum.Image.getText());
            map.put("appId", configurationService.getAppId());
            map.put("appSecret", configurationService.getAppSecret());


            map.put("imageString", imageString);


           // mqttPublishSample.publishTo_Delivery_web_server( JSON.toJSONString(map));




        }*/


    }
}