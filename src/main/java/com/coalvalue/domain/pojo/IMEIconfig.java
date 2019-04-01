package com.coalvalue.domain.pojo;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Created by yuan zhao  on 08/10/2015.
 */


public class IMEIconfig {
    private String alreadyConfigured;


    private String imei;
    private StatusInfo alreadyDocker;
    private StatusInfo alreadyProperty;
    private StatusInfo alreadyMqtt;
    private StatusInfo alreadyLocalMqtt;

    public IMEIconfig() {
    }

    public IMEIconfig(String imei) {
        this.imei = imei;
    }

    public IMEIconfig(String imei, String alreadyConfigured) {
        this.imei = imei;
        this.alreadyConfigured = alreadyConfigured;
    }

    public String getAlreadyConfigured() {
        return alreadyConfigured;
    }

    public void setAlreadyConfigured(String alreadyConfigured) {
        this.alreadyConfigured = alreadyConfigured;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public void setAlreadyDocker(StatusInfo alreadyDocker) {
        this.alreadyDocker = alreadyDocker;
    }

    public StatusInfo getAlreadyDocker() {
        return alreadyDocker;
    }

    public void setAlreadyProperty(StatusInfo alreadyProperty) {

        this.alreadyProperty = alreadyProperty;
    }

    public StatusInfo getAlreadyProperty() {
        return alreadyProperty;
    }

    public void setAlreadyMqtt(StatusInfo alreadyMqtt) {
        this.alreadyMqtt = alreadyMqtt;
    }

    public StatusInfo getAlreadyMqtt() {
        return alreadyMqtt;
    }

    public void setAlreadyLocalMqtt(StatusInfo alreadyLocalMqtt) {
        this.alreadyLocalMqtt = alreadyLocalMqtt;
    }

    public StatusInfo getAlreadyLocalMqtt() {
        return alreadyLocalMqtt;
    }
}
