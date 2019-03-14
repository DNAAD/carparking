package com.coalvalue.domain.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by yuan zhao  on 08/10/2015.
 */

@Entity

@Table(name = "preforence")

public class Preference extends BaseDomain {



    private String storageUuid;

    private String employeeUuid;

    public String getStorageUuid() {
        return storageUuid;
    }

    public void setStorageUuid(String storageUuid) {
        this.storageUuid = storageUuid;
    }

    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public void setEmployeeUuid(String employeeUuid) {
        this.employeeUuid = employeeUuid;
    }

    public Preference() {

    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }



    ;
}
