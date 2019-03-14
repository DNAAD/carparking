package com.coalvalue.domain.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Created by yuan zhao  on 08/10/2015.
 */

@Entity

@Table(name = "follower",catalog="storage")

public class Follower extends BaseDomain {



    @Column(name = "nickName")
    private String nickName;


    @Column(name = "openId")
    private String openId;

    @Column(name = "attend_time")
    private LocalDateTime attendTime;

    public LocalDateTime getAttendTime() {
        return attendTime;
    }

    public void setAttendTime(LocalDateTime attendTime) {
        this.attendTime = attendTime;
    }



    @Column(name = "status")
    private String status;




    public Follower() {
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
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

}
