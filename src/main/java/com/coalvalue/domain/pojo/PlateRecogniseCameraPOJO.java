package com.coalvalue.domain.pojo;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.time.LocalDateTime;

/**
 * Created by yuan zhao  on 08/10/2015.
 */


public class PlateRecogniseCameraPOJO {



    private String device_name;

    private String ipaddr;
    private String port;
    private String user_name;
    private String pass_wd;
    private String serialno;
    private Integer channel_num;
    private LocalDateTime timeStemp;

    public PlateRecogniseCameraPOJO() {
    }


    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPass_wd() {
        return pass_wd;
    }

    public void setPass_wd(String pass_wd) {
        this.pass_wd = pass_wd;
    }

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    public Integer getChannel_num() {
        return channel_num;
    }

    public void setChannel_num(Integer channel_num) {
        this.channel_num = channel_num;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }


    public void setTimeStemp(LocalDateTime timeStemp) {
        this.timeStemp = timeStemp;
    }

    public LocalDateTime getTimeStemp() {
        return timeStemp;
    }
}
