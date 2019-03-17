package com.coalvalue.service.assistant;

import com.alibaba.fastjson.JSON;
import com.coalvalue.configuration.MqttPublishSample;
import com.coalvalue.configuration.ReactorEventConfig;
import com.coalvalue.enumType.DataSynchronizationTypeEnum;
import com.coalvalue.notification.NotificationData;
import com.coalvalue.protobuf.Hub;
import com.coalvalue.publicCommand.PrivateNotify;
import com.coalvalue.publicCommand.PublicNotify;
import com.coalvalue.repository.EventRepository;
import com.coalvalue.service.*;
import com.coalvalue.service.assistant.LocalMqtt.CallBack;
import com.coalvalue.service.sync.DifferentialSyncService;
import com.coalvalue.task.InitTasks;
import com.coalvalue.task.RegisterTasks;
import com.coalvalue.task.SystemStatusBroadcast;


import org.eclipse.paho.client.mqttv3.*;

import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import reactor.bus.Event;
import reactor.bus.EventBus;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static com.coalvalue.configuration.WebSocketConfig.topic__COALPIT_DELIVERY_report;


@Component
public class ModuleMqttClientConfig {
    protected Logger logger = LoggerFactory.getLogger(getClass());


    @Value("${own.configuration.mqtt.local-broker.host}")
    public String HOST;

    public static final String TOPIC = "house/bulbs/bulb1";

    public static final String TOPIC_COMMAND = "house/bulbs/commad";
    private static final String clientid = "server";

    private MqttClient client;
    private MqttTopic topic;
    private String userName = "test";
    private String passWord = "test";

    private MqttMessage message;

    @Autowired
    private SystemStatusBroadcast systemStatusBroadcast;
   @Bean("mqttClientLocal")
 // @DependsOn({"broker"})
    public MqttClient server() {
        //MemoryPersistence设置clientid的保存形式，默认为以内存保存

       try {
           client = new MqttClient(HOST, clientid, new MemoryPersistence());

           System.out.println("---建立了 MQTTClient，  准备 连接 态");
       } catch (MqttException e) {
           System.out.println("-建 异常 ");
           e.printStackTrace();
       }





        return client;



    }


    @Recover
    public String recover(Throwable t) {
        logger.info("SampleRetryService.recover");
        return "Error Class :: " + t.getClass().getName();
    }


List<CallBack> callBacks = new ArrayList<>();

    MqttReceiver mqttReceiver = new MqttReceiver();
    @Retryable(
            value = {MqttException.class,UnknownHostException.class},
            maxAttempts = 4, backoff = @Backoff(2000))
    public void retryWhenException() throws  MqttException, UnknownHostException{

        logger.info("连接本地 MqttServer，建立本地 MqttClient");
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(userName);
        options.setPassword(passWord.toCharArray());
        // 设置超时时间
        options.setConnectionTimeout(10);
        // 设置会话心跳时间
        options.setKeepAliveInterval(20);


        client.setCallback(new PushCallback());


        client.connect(options);
        logger.info("连接本地本地连接成功 "+ client.getCurrentServerURI());


        String[] topics_UUID = new String[2];
        topics_UUID[0] = "health";
        topics_UUID[1] = "pong/host";

        int[] qos_UUID = new int[2];
        qos_UUID[0] = MqttPublishSample.qos_2;
        qos_UUID[1] = MqttPublishSample.qos_2;

        client.subscribe(topics_UUID,qos_UUID);

        client.subscribe("health",0);
        topic = client.getTopic(TOPIC);





    }

    public void ping(String dest,CallBack callBack) throws MqttException {

        message.setPayload("host".getBytes());
        callBacks.add(callBack);
        client.publish("ping/dest"+dest,message);


    }



        public void publish(MqttMessage message) throws MqttPersistenceException, MqttException {
        MqttDeliveryToken token = topic.publish(message);
        token.waitForCompletion();
        System.out.println(token.isComplete() + "========");
    }


    public void main(String[] args) throws MqttException {
        ModuleMqttClientConfig server = new ModuleMqttClientConfig();
        server.server();
        server.message = new MqttMessage();
        server.message.setQos(1);
        server.message.setRetained(true);
        server.message.setPayload("eeeeeaaaaaawwwwww---".getBytes());
        server.publish(server.message);
        System.out.println(server.message.isRetained() + "------ratained状态");


        class CallBackImpl implements CallBack {



            @Override
            public void messageArrived(String topic, MqttMessage message)  {
                // subscribe后得到的消息会执行到这里面
                System.out.println("接收消息主题:" + topic);
                System.out.println("接收消息Qos:" + message.getQos());
                System.out.println("接收消息内容:" + new String(message.getPayload()));
                String content = new String(message.getPayload());
                String[] s = content.split("#");
                String deviceId = s[0];
                if(topic.startsWith("ping")){
                    String dest = topic.split(":")[1];
                    if(dest.equals("host")){

                       // pong(new String(message.getPayload()));
                    }
                }

                if(topic.startsWith("pong")){
                    String dest = topic.split(":")[1];
                    if(dest.equals("host")){
                        //pong(new String(message.getPayload()));
                    }
                }



            }


        }





        ping("outhost",new CallBackImpl());
    }
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    public void chrome() {


        executorService.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        logger.info("启动本地 chrome 浏览器啊啊");
                                        MqttMessage message = new MqttMessage();
                                        message.setQos(1);
                                        message.setRetained(false);
                                        message.setPayload("chrome_up".getBytes());
                                        try {
                                            client.publish(TOPIC,message);
                                        } catch (MqttException e) {
                                            e.printStackTrace();
                                        }


                                        CallBack callBack = new CallBack(){
                                            @Override
                                            public void messageArrived(String string,MqttMessage message1){
                                                callBacks.remove(this);

                                                return;
                                            }
                                        };


                                        callBacks.add(callBack);

                                        try {
                                            wait(3000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                            callBacks.remove(callBack);
                                        }
                                    }

                                });




    }





    /**
     * 发布消息的回调类
     *
     * 必须实现MqttCallback的接口并实现对应的相关接口方法
     *      ◦CallBack 类将实现 MqttCallBack。每个客户机标识都需要一个回调实例。在此示例中，构造函数传递客户机标识以另存为实例数据。在回调中，将它用来标识已经启动了该回调的哪个实例。
     *  ◦必须在回调类中实现三个方法：
     *
     *  public void messageArrived(MqttTopic topic, MqttMessage message)
     *  接收已经预订的发布。
     *
     *  public void connectionLost(Throwable cause)
     *  在断开连接时调用。
     *
     *  public void deliveryComplete(MqttDeliveryToken token))
     *      接收到已经发布的 QoS 1 或 QoS 2 消息的传递令牌时调用。
     *  ◦由 MqttClient.connect 激活此回调。
     *
     */
    class PushCallback implements MqttCallback {

        public void connectionLost(Throwable cause) {
            // 连接丢失后，一般在这里面进行重连
            System.out.println("连接断开，可以做重连");
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            // subscribe后得到的消息会执行到这里面
            System.out.println("接收消息主题:" + topic);
            System.out.println("接收消息Qos:" + message.getQos());
            System.out.println("接收消息内容:" + new String(message.getPayload()));
            String content = new String(message.getPayload());
            String[] s = content.split("#");
            String deviceId = s[0];

         //   systemStatusBroadcast.update(deviceId,"online");

            callBacks.stream().forEach(e->e.messageArrived(topic,message));

        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
            // publish后会执行到这里
            System.out.println("deliveryComplete---------" + token.isComplete());
        }


    }





    public class MqttReceiver implements MqttCallbackExtended{//MqttCallback {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        protected transient Logger logger = LoggerFactory.getLogger(getClass());





        @Autowired
        InitTasks initTasks;
        @Autowired
        RegisterTasks registerTasks;



        @Override
        public void connectionLost(Throwable cause) {
            // TODO Auto-generated method stub
            System.out.println("Connection lost -=========== attempting reconnect.");

            NotificationData notificationData = new NotificationData();



        }


        @Override
        public void messageArrived(String topic, MqttMessage messageByte)
                throws Exception {


            cachedThreadPool.execute(new Runnable() {

                @Override
                public void run() {

                    System.out.println("收到信息=========="+topic+"========================================  "+topic);

                    if(topic.equals("")){

                        System.out.println("受到了 身份识别的 信息 来自 服务器的  进行身份 设置");




                    }




                    System.out.println("0000000000-000000000000000----------------------0"+message.toString());
                    System.out.println("0000000000-000000000000000----------------------0"+messageByte.getQos());
                    System.out.println("0000000000-000000000000000----------------------0"+messageByte.getId());

                    System.out.println("====synchronizedType=  isRetained "+topic);




                }
            });







        }


        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {

        }


        @Override
        public void connectComplete(boolean reconnect, String serverURI) {

            logger.debug("连接主机后 发送 上线通知 ");
            NotificationData notificationData = new NotificationData();
        }
    }
















}
