package com.coalvalue.python;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by silence on 2018-07-15.
 */
@Component
public class ScreanOnOffIPythonService implements IPythonService{


    @Autowired
    private ResourceLoader resourceLoader;


    String name = "on_off_screen.py";
    String id = "1";
    private Process process ;

    private ProcessBuilder pb;
    @Override
    public void execute(){


        Resource res = resourceLoader.getResource("classpath:python/onOffScreen/"+name);





        try{
        ProcessBuilder pb = new ProcessBuilder("python",res.getFile().getAbsolutePath());
            pb.directory(res.getFile().getParentFile());
            pb.redirectErrorStream(true);


        process = pb.start();



            System.out.println("process is is value is : "+process.toString());


            OutputHandler out
                    = new OutputHandler(process.getInputStream(), "UTF-8");
            OutputHandler err
                    = new OutputHandler(process.getErrorStream(), "UTF-8");
          out.join();
            System.out.println("Output:");
            System.out.println(out.getText());
            System.out.println();
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
        info.put("id",getId());


        info.put("fileName",name);
        info.put("status",process!= null ? process.isAlive()?"运行":"停止" :"未运行");
        return info;
    }

    @Override
    public void start() {

            if(pb == null && process== null){
                execute();
                //pb.start();

            }else{
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
        if(process!= null){
            return process.isAlive();

        }
       return false;
    }

    @Override
    public String getId() {
        return this.hashCode()+"";
    }

    @Override
    public String getConfigurationUrl() {
        return null;
    }
}
