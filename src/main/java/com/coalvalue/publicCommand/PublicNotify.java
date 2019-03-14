package com.coalvalue.publicCommand;

import com.coalvalue.enumType.EchoSessionTypeEnum;
import com.coalvalue.protobuf.Hub;
import com.coalvalue.task.InitTasks;
import com.coalvalue.task.RegisterTasks;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublicNotify  {

    @Autowired
    RegisterTasks registerTasks;
    @Autowired
    InitTasks initTasks;
    public PublicNotify() {}
 


    public boolean support(String topic) {
        return topic.equals("public");
    }

    public void decesion(Hub.Command command) {

        System.out.println("====公开 通道=  isRetained "+command.toString());


        if(command.getType().equals("echo_identity")){
  //          String echo_session = (String) command.get();.get("echo_session");

            try {
                initTasks.identity(EchoSessionTypeEnum.Identity_temp.getText());
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
        if(command.getType().equals("notify_message")){
            String message =  command.getMessage();
            System.out.println("====公开 公开 公开 notify_message "+message);

        }
        if(command.getType().equals("ping")){
            String message =  command.getMessage();
            System.out.println("====公开 公开 公开 notify_message "+message);
            try {
                initTasks.identity(EchoSessionTypeEnum.Identity_temp.getText());
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
        if(command.getType().equals("changeQrcode")){


            registerTasks.register(EchoSessionTypeEnum.Register_temp.getText());

            String message =  command.getMessage();
            System.out.println("====公开 公开 公开 notify_message "+message);
            //scheduledTasksMqtt.identity();
        }

    }

}