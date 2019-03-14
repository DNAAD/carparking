package com.coalvalue.task;

import com.alibaba.fastjson.JSON;
import com.coalvalue.configuration.ApplicationReadyEventListener;
import com.coalvalue.configuration.MqttConfiguration;
import com.coalvalue.configuration.MqttPublishSample;
import com.coalvalue.publicCommand.ParseRoute;
import com.coalvalue.repository.ConfigurationRepository;
import com.coalvalue.repository.InstanceTransportRepository;
import com.coalvalue.service.*;
import com.coalvalue.service.sync.DifferentialSyncService;
import com.coalvalue.service.sync.SynchronizationService;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.util.*;
import static com.coalvalue.configuration.WebSocketConfig.*;

@Component
public class SystemStatusBroadcast implements EnvironmentAware {



    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    MqttClient mqttClient;


    @Autowired
    ConfigurationService configurationService;
    @Autowired
    LiveInformationService liveInformationService;

    @Autowired
    ConfigurationRepository configurationRepository;


    @Autowired
    StorageService storageService;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    DistributorService distributorService;
    @Autowired
    MqttPublishSample mqttPublishSample;
    @Autowired
    InstanceTransportRepository instanceTransportRepository;
    @Autowired
    InitTasks scheduledTasksMqtt;
/*    @Autowired
            @Qualifier("embeddedBroker")
    MqttClient embeddedBroker;*/
    public static final String TOPIC = "house/bulbs/bulb1";
    @Autowired
    DifferentialSyncService differentialSyncService;


    Boolean completeEastanblishChanel = false;
    public boolean completeRegister = false;

    @Autowired
    public MqttClient mqttClientLocal;

    /**
     *注意重写的方法 setEnvironment 是在系统启动的时候被执行。
     */

    @Override
    public void setEnvironment(Environment environment) {


        //通过 environment 获取到系统属性.
        System.out.println(environment.getProperty("configuration.delay.time"));
        String delay =  environment.getProperty("configuration.delay.time");
/*
        if(delay !=null){
            delay_time =Integer.valueOf(delay);
        }else{
            delay_time =0;
        }
*/



        //通过 environment 同样能获取到application.properties配置的属性.
        System.out.println(environment.getProperty("spring.datasource.url"));


        //获取到前缀是"spring.datasource." 的属性列表值.
     /*         RelaxedPropertyResolver relaxedPropertyResolver = new RelaxedPropertyResolver(environment, "spring.datasource.");
              System.out.println("spring.datasource.url="+relaxedPropertyResolver.getProperty("url"));
       System.out.println("spring.datasource.driverClassName="+relaxedPropertyResolver.getProperty("driverClassName"));  */
    }





    public void reportFualtInfo(Integer integer) {
        Map content = new HashMap();
        content.put("id", integer);
        content.put("type", "status");
        content.put("mqttConnect",mqttClient.isConnected());
        content.put("establishChanellTryCount",integer);

        simpMessagingTemplate.convertAndSend(topic__COALPIT_DELIVERY_status, JSON.toJSON(content));
    }
    public Map getPageShownReportFualtInfo() {
        Map content = new HashMap();

        content.put("type", "status");
        content.put("mqttConnect",mqttClient.isConnected());
        content.put("establishChanellTryCount",1);
        return content;

    }

    public boolean canSync() {

        return  mqttClient.isConnected();
    }













    LocalDateTime preTime = LocalDateTime.now().minusMinutes(10);

    public void live(Long timeStamp) {
        Map map = new HashMap();
        preTime = LocalDateTime.now();
    }

    public Boolean isLive() {

        LocalDateTime now = LocalDateTime.now();
        if(now.minusSeconds(61).isBefore(preTime)){
            return true;
        }else{
            return false;
        }


    }










    public List<Map> gateWayinfo()  {
        ParseRoute pr = ParseRoute.getInstance();
        System.out.println( "Gateway: " + pr.getGateway() );
        System.out.println( "IP: " + pr.getLocalIPAddress() );
        pr.getGateway_ip().stream().forEach(e->System.out.println( "IP: " +e.toString()));
        List<Map> maps = new ArrayList<>();
        for(List<String> object: pr.getGateway_ip()){
            Map<String,Object> objectMap = new HashMap<>();
            objectMap.put("name",object.get(0));
            objectMap.put("url",object.get(1));
            maps.add(objectMap);
        }
        return maps;


    }



    public List<Map> info()  {
        //getServerIp();
        List<Map> maps = new ArrayList<>();
        Map<String,Object> objectMap = new HashMap<>();

        objectMap.put("name","配置文件");
        objectMap.put("url",ApplicationReadyEventListener.alreadyConfigured);
        maps.add(objectMap);

        objectMap = new HashMap<>();
        objectMap.put("name","与mqtt服务器连接");
        objectMap.put("url",mqttClient.isConnected());
        maps.add(objectMap);
        objectMap = new HashMap<>();
        objectMap.put("name","brokerIp");
        objectMap.put("url",mqttPublishSample.broker);
        maps.add(objectMap);


        objectMap = new HashMap<>();
        objectMap.put("name","是否在线");
        objectMap.put("url",isLive());
        maps.add(objectMap);


        objectMap = new HashMap<>();
        objectMap.put("name","建立通道");
        objectMap.put("url",completeEastanblishChanel);
        maps.add(objectMap);
        objectMap = new HashMap<>();
        objectMap.put("name","imei");
        objectMap.put("url",mqttPublishSample.imei);
        maps.add(objectMap);

        objectMap = new HashMap<>();
        objectMap.put("name","正在同步中的数据");
        objectMap.put("url",synchronizationService.processing.size());
        maps.add(objectMap);
        objectMap = new HashMap<>();
        objectMap.put("name","激活码");
        objectMap.put("url",scheduledTasksMqtt.activationCode);
        maps.add(objectMap);
        ;
        objectMap = new HashMap<>();
        objectMap.put("name","clientId");
        objectMap.put("url",MqttConfiguration.clientId);
        maps.add(objectMap);


        Object[] objects = getServerIp("wlan0");
        objectMap = new HashMap<>();
        objectMap.put("name","wlan0");

        objectMap.put("url",objects[1]);
        objectMap.put("status",objects[0]);
        objectMap.put("script","sudo wpa_cli -i wlan0 reconfigure");

        maps.add(objectMap);


        objects = getServerIp("eth0");
        objectMap = new HashMap<>();
        objectMap.put("name","eth0");
        objectMap.put("url",objects[1]);
        objectMap.put("status",objects[0]);
        maps.add(objectMap);


        objects = getServerIp("tun0");
        objectMap = new HashMap<>();
        objectMap.put("name","tun0");
        objectMap.put("url",objects[1]);
        objectMap.put("status",objects[0]);
        maps.add(objectMap);


        objectMap = new HashMap<>();
        objectMap.put("name","本地命令中心");
        objectMap.put("url","");
        objectMap.put("status",mqttClientLocal.isConnected());
        maps.add(objectMap);

        return maps;//new PageImpl<Map>(maps, pageable, maps.size());


    }

    public  Object[]   getServerIp(String name) {
        NetworkInterface nif = null;
        try {
            nif = NetworkInterface.getByName(name);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        Object[] objects = new Object[2];
        if(nif!= null){
            if(!nif.getInterfaceAddresses().isEmpty() ){
                objects[0]= (nif.getInterfaceAddresses().get(0).getAddress().getHostAddress());
                objects[1]= nif.getInterfaceAddresses().get(0).getAddress().getHostAddress();

            }else{
                try {
                    objects[0]= nif.isUp();

                    objects[1]= nif.isUp();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            }

        }else{
            objects[0]= "";

            objects[1]= "";
        }

        return objects;
    }
    @Autowired
    private SynchronizationService synchronizationService;







    String wlan_ip = null;
    String eth0_ip = null;




    /**
     * 获取服务器IP地址
     * @return
     */






        public  void   getServerIp(){



        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while(e.hasMoreElements())
            {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                System.out.println(n.getDisplayName());
                System.out.println("\t- name:" + n.getName());
                System.out.println("\t- idx:" + n.getIndex());
                System.out.println("\t- max trans unit (MTU):" + n.getMTU());
                System.out.println("\t- is loopback:" + n.isLoopback());
                System.out.println("\t- is PPP:" + n.isPointToPoint());
                System.out.println("\t- isUp:" + n.isUp());
                System.out.println("\t- isVirtual:" + n.isVirtual());
                System.out.println("\t- supportsMulticast:" + n.supportsMulticast());


                Enumeration ee = n.getInetAddresses();
                while (ee.hasMoreElements())
                {InetAddress i = (InetAddress) ee.nextElement();
                    if(n.getName().equals("wlan0") &&  i instanceof Inet4Address){
                        wlan_ip = i.getHostAddress();

                        break;
                    }
                    if(n.getName().equals("eth0") &&  i instanceof Inet4Address){
                        eth0_ip = i.getHostAddress();

                        break;
                    }

                    System.out.println(i.getHostAddress());
                }
            }


        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    Map deviceStatusMap = new HashMap();

    public void update(String deviceId, String online) {
        deviceStatusMap.put(deviceId,online);

    }
}

