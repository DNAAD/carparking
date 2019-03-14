package com.coalvalue.python;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * Created by silence on 2018-07-15.
 */
@Component
public class ObjectDetectionDeepLearningPythonService implements IPythonService{


    @Autowired
    private ResourceLoader resourceLoader;
    String id = "4";

    @Override
    public String getId() {
        return this.hashCode()+"";
    }

    @Override
    public String getConfigurationUrl() {
        return null;
    }

    String name = "deep_learning_object_detection.py";
    private Process process ;

    private ProcessBuilder pb;
    @Override
    public void execute(){

        Resource res = resourceLoader.getResource("classpath:python/object-detection-deep-learning/"+name);





//...其它参数添加

        List<String> commands=new ArrayList();


        String image = "images/example_06.jpg";
        String prototxt = "MobileNetSSD_deploy.prototxt.txt";
        String model = "MobileNetSSD_deploy.caffemodel";
        String confidence = "";

        commands.add("python");
        try {
            commands.add(res.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        commands.add("--prototxt");
        commands.add(prototxt);

        commands.add("--model");
        commands.add(model);
        commands.add("--image");
        commands.add(image);


        try{
            pb = new ProcessBuilder();

            pb.directory(res.getFile().getParentFile());

            pb.command(commands);
            //pb.directory(new File("classpath:python/object-detection-deep-learning/"));
            //ProcessBuilder pb = new ProcessBuilder("python",res.getFile().getAbsolutePath(),prototxt,model,image,confidence);
            String user_dir= System.getProperty("user.dir");
            System.out.println("执行任务E:\\directory\\src\\directory\\directory\\directory\\" +user_dir);
            pb.redirectErrorStream(true);
            process = pb.start();

/*
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));

            int ret = new Integer(in.readLine()).intValue();
*/

            System.out.println("process is is value is : "+process.toString());





            OutputHandler out
                    = new OutputHandler(process.getInputStream(), "UTF-8");
            OutputHandler err
                    = new OutputHandler(process.getErrorStream(), "UTF-8");
            System.out.println("out:"+out.getText());
            err.join();
            System.out.println("Error:");
            System.out.println(err.getText());


          //  int status = process.waitFor();
          //  System.out.println("Status: " + status);
         /*   out.join();
            System.out.println("Output:");
            System.out.println(out.getText());
            System.out.println();
            err.join();
            System.out.println("Error:");
            System.out.println(err.getText());*/



        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e);
        }


    }

    @Override
    public Map getInfo() {
        Map info = new HashMap();
        info = new HashMap<>();
        info.put("name",this.getClass().getName());
        info.put("fileName",name);
        info.put("id",getId());
        info.put("status",process!= null ? process.isAlive()?"运行":"停止" :"未运行");
        return info;
    }

    @Override
    public void start() {

            if(pb== null){
                execute();

            }else{
                try {
                    process = pb.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.print("error");

            }


    }

    @Override
    public void stop() {
            if(process== null){
                process.destroy();

            }else{
                System.out.print("error");
            }


    }

    @Override
    public boolean isLive() {
        return false;
    }

/*
    @Autowired
    @Qualifier("mqttClientLocal")

    MqttClient mqttClientLocal;
*/

    public void handler() {
        System.out.print("topic.识别到人， 然后 发送 控制 命令 (message);");
        MqttTopic topic = null;//mqttClientLocal.getTopic(ModuleMqttClientConfig.TOPIC_COMMAND);
        MqttMessage message = new MqttMessage();
        message.setQos(1);
        message.setRetained(true);
        message.setPayload("on".getBytes());
        try {
            topic.publish(message);
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }


    //@Scheduled(fixedRate = 10000)
    public void shedule() {
        System.out.print("topic.publish(message);");
        MqttTopic topic = null;//mqttClientLocal.getTopic(ModuleMqttClientConfig.TOPIC);
        MqttMessage message = new MqttMessage();
        message.setQos(1);
        message.setRetained(false);
        message.setPayload("eeeeeaaaaaawwwwww---".getBytes());
        try {
            topic.publish(message);
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }
}
