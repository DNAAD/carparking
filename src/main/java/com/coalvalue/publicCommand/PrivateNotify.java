package com.coalvalue.publicCommand;

import com.alibaba.fastjson.JSON;
import com.coalvalue.configuration.Docker;
import com.coalvalue.configuration.MqttPublishSample;
import com.coalvalue.enumType.CommandEnum;
import com.coalvalue.enumType.DataSynchronizationTypeEnum;
import com.coalvalue.enumType.EchoSessionTypeEnum;
import com.coalvalue.protobuf.Hub;
import com.coalvalue.repository.*;
import com.coalvalue.service.ConfigurationService;
import com.coalvalue.service.strategy.StrategyService;
import com.coalvalue.service.sync.DifferentialSyncService;
import com.coalvalue.task.InitTasks;
import com.coalvalue.task.RegisterTasks;
import com.coalvalue.web.HardWareCommandService;
import com.coalvalue.web.MobileIndexController;
import com.google.protobuf.InvalidProtocolBufferException;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static com.coalvalue.configuration.WebSocketConfig.*;
@Component
public class PrivateNotify {

    @Autowired
    ConfigurationRepository configurationRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    StorageSpaceRepository storageSpaceRepository;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ReportDeliveryOrderRepository reportDeliveryOrderRepository;

    @Autowired
    RegisterTasks registerTasks;

    @Autowired
    InitTasks initTasks;

    @Autowired
    ConfigurationService configurationService;


    @Autowired
    DifferentialSyncService differentialSyncService;
    @Autowired
    HardWareCommandService hardWareCommandService;


    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private StrategyService strategyService;



    @Autowired
    private CommonProcess commonProcess;

    private String topic
            ;


    public PrivateNotify() {}
 


    public void decesion(Hub.Command command) {
        System.out.println("====私自，私自，私自 notify_message "+command.getType());



/*        if(DataSynchronizationTypeEnum.Sync.getText().equals(m.getType())){ // 服务器单独发来的同步信息
            System.out.println("***********************收到同步 要求 **************************");


            NotificationData_sync notificationData = new NotificationData_sync();
            notificationData.setObject(map);
            eventBus.notify(ReactorEventConfig.notificationConsumer_sync_event, Event.wrap(notificationData));
        }*/

/*        if(DataSynchronizationTypeEnum.TimeSlice.getText().equals(m.getType())){
            String timeSlice_string =(String)map.get("timeSlice");
            System.out.println("------------------------------ 更新时间片信息："+ timeSlice_string);
            timeSilcePasswordService.syncImmediately(timeSlice_string);
            TimeSilcePasswordService.tiemSliceReady = true;
        }


        if(DataSynchronizationTypeEnum.Live.getText().equals(m.getType())){
            Long timeStamp =(Long)map.get("timeStamp");
            System.out.println("------------------------------ 更新时间片信息："+ timeStamp);
            systemStatusBroadcast.live(timeStamp);
            liveBroadcast.reportCurrentLiveEvent(null);
        }*/

        String type = command.getType();

        if(type.equals(CommandEnum.clear.getText())){
            String message = command.getMessage();
            System.out.println("====私自，私自，私自 notify_message "+message);
            commonProcess.deleteAll();

        }
        if(type.equals(CommandEnum.bind.getText())){
            String message = command.getMessage();
            System.out.println("====私自，私自，私自 notify_message "+message);
            commonProcess.deleteAll();

            registerTasks.register(EchoSessionTypeEnum.Register_bootup.getText());

          //  differentialSyncService.differentialSync(EchoSessionTypeEnum.Whole.getText(),"group");

/*            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            scheduledTasksMqtt.identity();*/
        }

        if(type.equals("shell__")){
            String message = command.getMessage();

            ProcessBuilder pb = new ProcessBuilder("/usr/local/bin/wkhtmltopdf", "http://www.yahoo.com", "/tmp/yahoo.pdf");





            pb =
                    new ProcessBuilder("myCommand", "myArg1", "myArg2");
            Map<String, String> env = pb.environment();
            env.put("VAR1", "myValue");
            env.remove("OTHERVAR");
            env.put("VAR2", env.get("VAR1") + "suffix");
            pb.directory(new File("myDir"));
            File log = new File("log");
            pb.redirectErrorStream(true);
            pb.redirectOutput(ProcessBuilder.Redirect.appendTo(log));
            Process p = null;
            try {
                p = pb.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert pb.redirectInput() == ProcessBuilder.Redirect.PIPE;
            assert pb.redirectOutput().file() == log;
          //  assert p.getInputStream().read() == -1;



            System.out.println("====私自，私自，私自 notify_message "+message);
            commonProcess.deleteAll();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                initTasks.identity(EchoSessionTypeEnum.Identity_temp.getText());
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }

        if(type.equals(CommandEnum.shell_command.getText())){
            String message = command.getMessage();
            hardWareCommandService.remoteShellExecute(message);

           // org.h2.tools.DeleteDbFiles.execute(dbDir, dbName, true);
        }

        if(type.equals(CommandEnum.shell_script_command.getText())){
            String message = command.getMessage();
            hardWareCommandService.remoteShellScriptExecute(message);

        }

        if(type.equals("unbind")){

            commonProcess.deleteAll();

            Map ret = new HashMap<String, String>();
            ret.put("status", false);
            Map content = new HashMap();

            content.put("type", "redirect");

            Map focus = MobileIndexController.dynamic.stream().filter(e->e.get("name").equals("welcome")).findAny().get();
            String focusRedirectUrl = linkTo(methodOn(MobileIndexController.class).focusRedirect((String)focus.get("uuid"))).toUri().toString();

            content.put("content",focusRedirectUrl);

            simpMessagingTemplate.convertAndSend(topic__COALPIT_DELIVERY_welcome, JSON.toJSON(content));
        }



        if(type.equals(CommandEnum.image.getText())) {
            try {
                Hub.ImageStrategy imageStrategy = Hub.ImageStrategy.parseFrom(command.getBolb().toByteArray());
                configurationService.createImageStrategy(imageStrategy);
                configurationService.givenUsingTimer_whenStoppingThread_thenTimerTaskIsCancelled();
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            strategyService.command(StrategyService.Strategy_COMMAND.Strategy_COMMAND_极少探测_openvpn, "UUID");
        }
        if(type.equals("出库")) {
            String message = command.getMessage();
            strategyService.command(StrategyService.Strategy_COMMAND.Strategy_COMMAND_过磅,message);
        }

        if(type.equals("docker")) {
            String message = command.getMessage();
       //     docker.all_together____(message);
        }


    }




    public boolean support(Hub.Message message) {

        if(DataSynchronizationTypeEnum.Command.getText().equals(message.getType())){
            return true;
        }
        return false;

    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}


