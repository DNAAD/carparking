package com.coalvalue.python;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by silence on 2018-07-15.
 */
@Component
public class LicensePlateDetectionIPythonService implements IPythonService{


    @Autowired
    private ResourceLoader resourceLoader;

    String id = "2";

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getConfigurationUrl() {
        return null;
    }

    public void setId(String id) {
        this.id = id;
    }

    String name = "detect_opencv33";
    private Process process ;

    private ProcessBuilder pb;
    @Override
    public void execute(){

        Resource res = resourceLoader.getResource("classpath:python/Mobilenet-SSD-License-Plate-Detection-master/"+name);

        //String pathPrefix = ResourceUtils.getURL("classpath:").getPath();


        //String user_dir= System.getProperty("user.dir");
        try {
            System.out.println("执行任务E:\\carparking\\src\\main\\resources\\proto\\"+res.getURI().toString());
            System.out.println("执行任务E:\\carparking\\src\\main\\resources\\proto\\"+res.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("执行任务E:\\carparking\\src\\main\\resources\\proto\\");


        try{
        ProcessBuilder pb = new ProcessBuilder("python",res.getFile().getAbsolutePath(),""+1,""+2);

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
        info.put("name","qrcodeScan");
        info.put("fileName",name);
        info.put("status",process!= null ? process.isAlive()?"运行":"停止" :"未运行");
        return info;
    }

    @Override
    public void start() {
        try {
            if(process== null){
                pb.start();

            }else{
                System.out.print("error");

            }
        } catch (IOException e) {
            e.printStackTrace();
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
        if(process!= null){
            return process.isAlive();

        }
        return false;
    }
}
