package com.coalvalue.python;

import com.coalvalue.python.plate.PlateIServerController;
import com.coalvalue.python.plate.PlateTcpControlMain;
import com.coalvalue.python.server.ServerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by silence on 2018-07-15.
 */
@Component
public class LicensePlateDetectionIJavaService implements IPythonService{


    public static final String namespace = "plate/";
    @Autowired
    private PlateTcpControlMain plateTcpControlMain;


    String id = this.hashCode()+"";

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String name = "detect_opencv33";

    String type = ServerType.JAVA.name();







    @Override
    public void execute(){


        System.out.println("执行任务E:\\carparking\\src\\main\\resources\\proto\\");



    }

    @Override
    public Map getInfo() {
        Map info = new HashMap();
        info = new HashMap<>();
        info.put("name",this.getClass().getName());
        info.put("fileName",name);
        info.put("id",id);
        info.put("status","停止,未运行");
        info.put("type",type);
        info.put("url",getConfigurationUrl());

        return info;
    }

    @Override
    public void start() {

        System.out.print("error");


    }

    @Override
    public void stop() {


    }

    @Override
    public boolean isLive() {

        return false;
    }

    @Override
    public String getConfigurationUrl() {
        return  linkTo(methodOn(PlateIServerController.class).index("")).withSelfRel().getHref();
    }





}
