package com.coalvalue.domain.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by yuan zhao  on 08/10/2015.
 */

@Entity

@Table(name = "loadometer",catalog="storage")

public class Loadometer extends BaseDomain {



    @Column(name = "no")
    private String no;

    @Column(name = "entrance_exit_type")
    private String entranceExitType;


    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getEntranceExitType() {
        return entranceExitType;
    }

    public void setEntranceExitType(String entranceExitType) {
        this.entranceExitType = entranceExitType;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }


}
