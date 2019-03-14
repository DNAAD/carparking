package com.coalvalue.publicCommand;

import ch.qos.logback.core.subst.Tokenizer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;

import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Map;

@Component
public class CommandLinux {



    public void on(String s) {


        ProcessBuilder pb = new ProcessBuilder("vcgencmd", "display_power", "1");



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



    }


    public void off(String s) {


    ProcessBuilder pb = new ProcessBuilder("vcgencmd", "display_power", "0");




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



    }

    public Boolean check() {


        ProcessBuilder pb = new ProcessBuilder("vcgencmd", "display_power");

/*

        Map<String, String> env = pb.environment();

        pb.redirectErrorStream(true);
        pb.redirectOutput(ProcessBuilder.Redirect.appendTo(log));
*/




        Process p = null;
        try {
            p = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

        try{
            while (stdInput.ready()) { //While there's something in the buffer
                //read&print - replace with a buffered read (into an array) if the output doesn't contain CR/LF
                System.out.println(stdInput.readLine());
                String readLine = stdInput.readLine();
                String[] result = readLine.split("=");
                if(result[1].equals("0")){
                    return true;
                }else{
                    return false;
                }

            }
        }catch (IOException e){
            e.printStackTrace();
        }

        p.destroy(); //The buffer is now empty, kill the process.



        return null;
    }
    public Boolean processIsRunning() {
        String line = null;
        try {
            //pgrep -fl java | awk {'print $1'}
            Process p = Runtime.getRuntime().exec("ps -ef | grep Xvfb | grep -v grep | awk {'print $2'}");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null)
            {
                System.out.println(line);
            }
        } catch (Exception err) {
            System.out.println(err);
        }
        return line != null;
    }


    private void parseLinux()
    {
        ParseRoute pr = ParseRoute.getInstance();
        System.out.println( "Gateway: " + pr.getGateway() );
        System.out.println( "IP: " + pr.getLocalIPAddress() );
    }

}