package com.coalvalue.domain.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.sql.Blob;

/**
 * Created by yuan zhao  on 08/10/2015.
 */

@Entity


@Table(name = "configuration",catalog="storage")
public class Configuration extends BaseDomain {

    @Column(name = "user_Id")
    private Integer userId;


    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "mode")
    private String mode;

    @Column(name = "key_word")
    private String key;

    @Column(name = "value")
    private String value;

    @Column(name = "string_value")
    private String stringValue;
    @Column(name = "int_value")
    private Integer intValue;
    @Column(name = "boolean_value")
    private Boolean booleanValue;
    private String type;
    @Lob
    @Column(name = "blob__", columnDefinition="BLOB")
    private byte[] blob;




    public byte[] getBlob() {
        return blob;
    }

    public void setBlob(byte[] blob) {
        this.blob = blob;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Integer getIntValue() {
        return intValue;
    }

    public void setIntValue(Integer intValue) {
        this.intValue = intValue;
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public Configuration() {
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    ;
}
