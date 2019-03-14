package com.coalvalue.web;

import com.coalvalue.domain.entity.Configuration;
import com.coalvalue.domain.entity.StorageSpace;
import com.coalvalue.repository.ConfigurationRepository;
import com.coalvalue.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.coalvalue.python.OutputHandler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by silence on 2018-07-15.
 */
@Component
public class HardWareCommandService {


    @Autowired
    private ResourceLoader resourceLoader;

    String id = "2";




    public void setId(String id) {
        this.id = id;
    }

    String name = "kiosk_chromium_browser.sh";
    private Process process ;

    private ProcessBuilder pb;
    public void execute(){

        Resource res = resourceLoader.getResource("classpath:shell/"+name);

        //String pathPrefix = ResourceUtils.getURL("classpath:").getPath();




      //  File file=new File("d:\\\\"+name);

        File file=new File("/home/pi/shell",name);

        file.getParentFile().mkdirs();
            System.out.println("n$$$$$$$$$$$$$$$$$ew File:\\carparking\\src\\main\\resources\\proto\\"+file.toString());
            if(!file.exists())
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }
            System.out.println("n$$$$$$$$$$$$$$$$$ew File:\\carparking\\src\\main\\resources\\proto\\"+file.toString());


            System.out.println("================:\\carparking\\src\\main\\resources\\proto\\"+file.toString());






        try {
            InputStream in = res.getInputStream();
            FileOutputStream out=new FileOutputStream(file);
            int c;
            byte buffer[]=new byte[1024];
            while((c=in.read(buffer))!=-1){
                for(int i=0;i<c;i++)
                    out.write(buffer[i]);
            }
            in.close();
            out.close();

        } catch (IOException e){
            e.printStackTrace();
        }


        //String user_dir= System.getProperty("user.dir");
        //System.out.println("执行任务E:\\carparking\\src\\main\\resources\\proto\\"+file.getURI().toString());





        System.out.println("执行任务E:\\carparking\\src\\main\\resources\\proto\\"+file.getAbsolutePath());
        System.out.println("执行任务E:\\carparking\\src\\main\\resources\\proto\\");
    //    ProcessBuilder pb = new ProcessBuilder("sudo -u pi bash",file.getAbsolutePath(),""+1,""+2);
        ProcessBuilder pb = new ProcessBuilder("sudo","-u","pi","bash",file.getAbsolutePath(),""+1,""+2);

        pb.redirectErrorStream(true);

        try{

        process = pb.start();

        System.out.println("process is is value is : "+process.toString());




            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("ddddddddddddd"+line);
            }
            int exitCode = process.waitFor();
            System.out.println("exitCode = "+exitCode);


/*

            OutputHandler out   = new OutputHandler(process.getInputStream(), "UTF-8");
            OutputHandler err
                    = new OutputHandler(process.getErrorStream(), "UTF-8");

*/




        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e);
        }


    }


    public Map getInfo() {
        Map info = new HashMap();
        info = new HashMap<>();
        info.put("name","qrcodeScan");
        info.put("fileName",name);
        info.put("status",process!= null ? process.isAlive()?"运行":"停止" :"未运行");
        return info;
    }


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




    public boolean isLive() {
        if(process!= null){
            return process.isAlive();

        }
        return false;
    }








    public void remoteShellExecute(String command){




        //  File file=new File("d:\\\\"+name);

        File file=new File("/home/pi/shell","tempShell.sh");

        file.getParentFile().mkdirs();
        if(!file.exists())
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }








        try {

            FileOutputStream out=new FileOutputStream(file);
            PrintStream ps = new PrintStream(out);

                    ps.print(command);


            out.close();

        } catch (IOException e){
            e.printStackTrace();
        }


        //String user_dir= System.getProperty("user.dir");
        //System.out.println("执行任务E:\\carparking\\src\\main\\resources\\proto\\"+file.getURI().toString());





        System.out.println("执行任务E:\\carparking\\src\\main\\resources\\proto\\"+file.getAbsolutePath());

        //    ProcessBuilder pb = new ProcessBuilder("sudo -u pi bash",file.getAbsolutePath(),""+1,""+2);
        ProcessBuilder pb = new ProcessBuilder("sudo","-u","pi","bash",file.getAbsolutePath(),""+1,""+2);

        pb.redirectErrorStream(true);

        try{

            process = pb.start();

            System.out.println("process is is value is : "+process.toString());




            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("ddddddddddddd"+line);
            }
            int exitCode = process.waitFor();
            System.out.println("exitCode = "+exitCode);


/*

            OutputHandler out   = new OutputHandler(process.getInputStream(), "UTF-8");
            OutputHandler err
                    = new OutputHandler(process.getErrorStream(), "UTF-8");

*/




        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e);
        }


    }



    private static Process chromeProcess ;
    public void stopChrome() {
        if(chromeProcess== null){
            chromeProcess.destroy();

        }else{
            System.out.print("error");
        }


    }
    public  void remoteChrome()throws Exception {


    }








    public void remoteShellScriptExecute(String command){


        File file=new File("/home/pi/shellScript","tempShell.sh");

        file.getParentFile().mkdirs();
        if(!file.exists())
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }








        try {

            FileOutputStream out=new FileOutputStream(file);
            PrintStream ps = new PrintStream(out);

            ps.print(command);


            out.close();

        } catch (IOException e){
            e.printStackTrace();
        }



        System.out.println(file.getAbsolutePath());

        //    ProcessBuilder pb = new ProcessBuilder("sudo -u pi bash",file.getAbsolutePath(),""+1,""+2);
        ProcessBuilder pb = new ProcessBuilder("cmd",file.getAbsolutePath(),""+1,""+2);

        pb.redirectErrorStream(true);

        try{

            process = pb.start();

            System.out.println("process is is value is : "+process.toString());




            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = process.waitFor();
            System.out.println("exitCode = "+exitCode);


        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e);
        }


    }


}
