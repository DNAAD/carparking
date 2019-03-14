package com.coalvalue.python.plate;

import com.coalvalue.configuration.ReactorEventConfig;
import com.coalvalue.domain.entity.*;
import com.coalvalue.domain.pojo.PlateRecogniseCameraPOJO;
import com.coalvalue.enumType.EquipmentTypeEnum;
import com.coalvalue.enumType.PlateDirectionEnum;
import com.coalvalue.notification.NotificationData_loadometer;
import com.coalvalue.repository.LoadometerRepository;
import com.coalvalue.repository.PlateRecognitionRepository;
import com.coalvalue.service.DeliveryOrderService;
import com.coalvalue.service.EquipmentService;
import com.coalvalue.service.InstanceTransportService;
import com.coalvalue.service.LoadometerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.bus.Event;
import reactor.bus.EventBus;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static com.coalvalue.python.plate.GetRequestJsonUtils.getRequestJsonString;

/**
 * Servlet implementation class PlateServlet /devicemanagement/php/plateresult.php
 * /devicemanagement/php/receivedeviceinfo.php
 */

@Controller
//@WebServlet("/plate") //description = "a enter for wechat", urlPatterns = { "/aaa"},loadOnStartup=1
@RequestMapping(value= {"/plateServlet"})
public class PlateServletController {
    private static final long serialVersionUID = 1L;


    @Autowired
    PlateRecognitionRepository plateRecognitionRepository;

    @Autowired
    DeliveryOrderService deliveryOrderService;

    @Autowired
    LoadometerRepository loadometerRepository;

    @Autowired
    LoadometerService loadometerService;
    @Autowired
    InstanceTransportService instanceTransportService;



    @Autowired
    EventBus eventBus;
    @Autowired
    EquipmentService equipmentService;



    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ResponseBody
    public Map doGet(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        // response.getWriter().append("Served at: ").append(request.getContextPath());

        // 回复命令，控制设备开闸
/*        response.setContentType("text/json");
        PrintWriter out = response.getWriter();
        out.println("{\"Response_AlarmInfoPlate\":{\"info\":\"ok\",\"content\":\"...\",\"is_pay\":\"true\"}}");
        out.flush();
        out.close()*/;
        Map map = new HashMap();
        map.put("message","{\"Response_AlarmInfoPlate\":{\"info\":\"ok\",\"content\":\"...\",\"is_pay\":\"true\"}}");

        return map;

    }


    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public void doPost(PlateRecogniseCameraPOJO device_name, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        System.out.println("===============================+=======================");

        Equipment equipment = equipmentService.createEquipment(device_name.getSerialno(), EquipmentTypeEnum.License_Plate_Capture_Cameras);
        equipmentService.updateEquipmentLiveInfo(equipment,device_name);

        System.out.println("===============================+======================="+device_name.toString());
        // 把接收到车牌结果保存到txt文件中
        try {
            //JsonParser parser = new JsonParser();  //创建JSON解析器


        }catch (Exception e){


        }

        }


    @Transactional
    public PlateRecognition createPlateRecognition(Equipment equipment, String license, Integer direction, String confidence, String carColor) {

        PlateRecognition plateRecognition = new PlateRecognition();

        plateRecognition.setDeviceId(equipment.getDeviceId());

        plateRecognition.setDirection(PlateDirectionEnum.fromInt(direction).getText());

        plateRecognition.setLicense(license);
        plateRecognition.setColourCode(1);
        plateRecognition.setPath("");

        System.out.println("存储车牌号 createPlateRecognition："+plateRecognition.toString());
        System.out.println("存储车牌号 createPlateRecognition："+plateRecognition.getDirection());

        Loadometer loadometer = loadometerService.findByCamera(equipment.getName());


        plateRecognition = plateRecognitionRepository.save(plateRecognition);
        if(plateRecognition.getDirection().equals(PlateDirectionEnum.down.getText())){
            System.out.println("存down储车牌号 createPlateRecdownognition："+plateRecognition.getDirection());
            ReportDeliveryOrder reportDeliveryOrder = deliveryOrderService.getValidByLicense(plateRecognition.getLicense());
            NotificationData_loadometer notificationData = new NotificationData_loadometer();
            notificationData.setObject(plateRecognition);
            notificationData.setDeliveryOrder(reportDeliveryOrder);
            notificationData.setLoadometer(loadometer);
            eventBus.notify(ReactorEventConfig.notificationConsumer_delivery_order_in_on_weight_event, Event.wrap(notificationData));

        }
        System.out.println("存储车牌号 createPlateRecognition："+plateRecognition.getDirection());
        if(plateRecognition.getDirection().equals(PlateDirectionEnum.up.getText())){
            System.out.println("存up储车牌号 createPlateRecognition："+plateRecognition.getDirection());
            InstanceTransport instanceTransport = instanceTransportService.getLoadingByLicense(plateRecognition.getLicense());
            NotificationData_loadometer notificationData = new NotificationData_loadometer();
            notificationData.setObject(plateRecognition);
            notificationData.setInstanceTransport(instanceTransport);
            notificationData.setLoadometer(loadometer);
            eventBus.notify(ReactorEventConfig.notificationConsumer_create_instance_transport_out_on_weight_event, Event.wrap(notificationData));
        }




        return plateRecognition;

    }


}
