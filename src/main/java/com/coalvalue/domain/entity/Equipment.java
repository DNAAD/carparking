package com.coalvalue.domain.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * Created by yuan zhao  on 08/10/2015.
 */

@Entity

@Table(name = "equipment",catalog="storage")

public class Equipment extends BaseDomain {


    @Column(name = "pin")
    private String pin;


    @NotNull
    @Column(name = "name")
    private String name;
    @NotNull
    @Column(name = "status")
    private String status;

    @NotNull
    private String type;

    @NotNull
    private String deviceId;

    @NotNull
    private String hubId;


    public Equipment() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setHubId(String hubId) {
        this.hubId = hubId;
    }

    public String getHubId() {
        return hubId;
    }


    ;
}
