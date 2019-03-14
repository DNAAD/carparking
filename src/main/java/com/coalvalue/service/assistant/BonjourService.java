package com.coalvalue.service.assistant;

import com.coalvalue.publicCommand.ParseRoute;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

@Component
public class BonjourService{// extends AssistantService{
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

   /* @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public String status() {
        return null;
    }
    @Override
    public String info() {
        return "";
    }

    @Override
    public boolean isProcessAlive(Process process) {
        return false;
    }

    @Override
    public String doInBackground(Object... params) {
        return null;
    }

    @Override
    public String restart() {
        return null;
    }*/

    private static class SampleListener implements ServiceListener {
        @Override
        public void serviceAdded(ServiceEvent event) {
            try {
                System.out.println("Service added: " + event.getInfo());
                System.out.println("---: " + event.getInfo().getKey());
                System.out.println("---: " + event.getInfo().getName());
                System.out.println("---: " + event.getInfo().getType());
                System.out.println("---: " + event.getInfo().getDomain());
                System.out.println("---: " + event.getInfo().getProtocol());
                System.out.println("---: " + event.getInfo().getApplication());
                System.out.println("---: " + event.getInfo().getSubtype());
                System.out.println("---:getServer " + event.getInfo().getServer());
                System.out.println("---: " + event.getInfo().getPort());
                System.out.println("---: " + event.getInfo().getWeight());
                System.out.println("---: Priority" + event.getInfo().getPriority());
                System.out.println("---: getHostName" + event.getDNS().getHostName());
                System.out.println("---: getName" + event.getDNS().getName());
                ;
                for(String url: event.getInfo().getURLs()){
                    System.out.println("---: url" + url);
                }
                for(InetAddress url: event.getInfo().getInetAddresses()){
                    System.out.println("---: InetAddress" + url.getHostAddress());
                }
            }catch (Exception e){
                e.printStackTrace();
            }

/*
            Arrays.stream(event.getInfo().getURLs()).forEach(e->System.out.println("---: getURLs:" +e.toString() ));
            Arrays.stream(event.getInfo().getInetAddresses()).forEach(e->System.out.println("---: getInetAddresses:" +e.toString() ));
*/

        }

        @Override
        public void serviceRemoved(ServiceEvent event) {
            System.out.println("Service removed: " + event.getInfo());
        }

        @Override
        public void serviceResolved(ServiceEvent event) {
            try {
                System.out.println("Service resolved: " + event.getInfo());
                System.out.println("---: " + event.getInfo().getKey());
                System.out.println("---: " + event.getInfo().getName());
                System.out.println("---: " + event.getInfo().getType());
                System.out.println("---: " + event.getInfo().getDomain());
                System.out.println("---: " + event.getInfo().getProtocol());
                System.out.println("---: " + event.getInfo().getApplication());
                System.out.println("---: " + event.getInfo().getSubtype());
                System.out.println("---:getServer " + event.getInfo().getServer());
                System.out.println("---: " + event.getInfo().getPort());
                System.out.println("---: " + event.getInfo().getWeight());
                System.out.println("---: Priority" + event.getInfo().getPriority());
                System.out.println("---: getHostName" + event.getDNS().getHostName());
                System.out.println("---: getName" + event.getDNS().getName());
                ;

                for (String url : event.getInfo().getURLs()) {
                    System.out.println("---: url" + url);
                }
                for (InetAddress url : event.getInfo().getInetAddresses()) {
                    System.out.println("---: InetAddress" + url.getHostAddress());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
/*
            Arrays.stream(event.getInfo().getURLs()).forEach(e->System.out.println("---: getURLs:" +e.toString() ));
            Arrays.stream(event.getInfo().getInetAddresses()).forEach(e->System.out.println("---: getInetAddresses:" +e.toString() ));
*/


        }
    }
    JmDNS jmdns = null;
    String type_image = "_image._tcp.local.";
    @PostConstruct
    public void init() {
        try {
            jmdns = JmDNS.create(InetAddress.getLocalHost());
        } catch (IOException e) {
            e.printStackTrace();
        }

        registerService();
    }




    public void addServiceListener()  {
        executorService.execute(new Runnable() {
            @Override
            public void run() {


                System.out.println("初始化 JMDNS ");

                jmdns.addServiceListener(type_image, new SampleListener());
                // ServiceInfo[] serviceInfos = jmdns.list(type);
                System.out.println("初始化 JMDNS 结束 ");

                // Wait a bit
                try {
                    Thread.sleep(3000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



            }
        });

    }

    public PageImpl requestPage(Pageable pageable)  {
        System.out.println(" requestPage 结束 "+ jmdns.toString());
        List<Map> maps = new ArrayList<>();

        ServiceInfo[] serviceInfos = jmdns.list(type_image,1);
        for (ServiceInfo info : serviceInfos) {
         //   jmdns.requestServiceInfo();
            System.out.println("## resolve service " + info.getKey()+info.getName()  + " : " + info.getURL());
            Map<String,Object> objectMap = new HashMap<>();
            objectMap.put("key", info.getKey());
            objectMap.put("name", info.getName());
            objectMap.put("type", info.getType());
            objectMap.put("domain", info.getDomain());
            objectMap.put("protocol", info.getProtocol());
            objectMap.put("application", info.getApplication());
            objectMap.put("subtype", info.getSubtype());
            objectMap.put("server", info.getServer());
            objectMap.put("port", info.getPort());
            objectMap.put("weight", info.getWeight());
            objectMap.put("priority", info.getPriority());
            objectMap.put("hostAddresses", info.getHostAddresses().toString());

            objectMap.put("url", Arrays.stream(info.getURLs()).map(e->e.toString()).collect(Collectors.toList()).toString());
            maps.add(objectMap);
        }


        System.out.println("------------"+pageable.toString());
        return new PageImpl<Map>(maps, pageable, maps.size());
    }


    public static void main(String[] args) throws InterruptedException {
        try {
            // Create a JmDNS instance
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
            String bonjourServiceType = "_http._tcp.local.";

            String bonjourServiceType_VNC = "_rfb._tcp.local.";
            String bonjourServiceType_SSH = "_ssh._tcp.local.";
            String bonjourServiceType_Apache = "_http._tcp.local.";
            String type = "_workstation._tcp.local.";

                    String type_image = "_image._tcp.local.";
            // Add a service listener
            jmdns.addServiceListener(type_image, new SampleListener());


            ServiceInfo[] serviceInfos = jmdns.list(type_image);
            for (ServiceInfo info : serviceInfos) {
                System.out.println("## resolve service " +info.getKey()+ info.getName()  + " : " + info.getURL());
            }

            //

/*            For VNC, use _rfb._tcp.local.
                    For SSH, use _ssh._tcp.local.
                    For Apache, use _http._tcp.local.*/


            // Wait a bit
            Thread.sleep(3000000);
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }





    public void registerService() {

        try {
            // Create a JmDNS instance
            String type_image = "_yulinmei._tcp.local.";
            // Register a service
            ServiceInfo serviceInfo = ServiceInfo.create(type_image, "yulinmei", 1234, "path=index.html");
            jmdns.registerService(serviceInfo);
/*
            // Wait a bit
            Thread.sleep(1000000);
            //java.net.NetworkInterface.isUp();
            // Unregister all services
            jmdns.unregisterAllServices();*/

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void unregisterAllServices() {
        jmdns.unregisterAllServices();

    }


}