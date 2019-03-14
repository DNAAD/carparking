package com.coalvalue.domain.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by yuan zhao  on 10/18/2015.
 */
@Entity
@Table(name = "camera",catalog="storage")
public class Camera extends BaseDomain {

    @Column(name = "no")
    private String no;



    @Column(name = "rstp_url")
    private String rstpUrl;
    @Column(name = "path")
    private String path;
    private String username;
    private String ip;
    private String password;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Column(name = "status")
    private String status;
    @Column(name = "note")
    private String note;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;


    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getRstpUrl() {
        return rstpUrl;
    }

    public void setRstpUrl(String rstpUrl) {
        this.rstpUrl = rstpUrl;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Camera() {
    }


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
