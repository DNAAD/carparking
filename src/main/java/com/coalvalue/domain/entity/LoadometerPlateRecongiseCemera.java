package com.coalvalue.domain.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by yuan zhao  on 08/10/2015.
 */

@Entity

@Table(name = "loadometer_plate_recongise_cemera",catalog="storage")

public class LoadometerPlateRecongiseCemera extends BaseDomain {



    @Column(name = "loadometer_no")
    private String loadometerNo;

    @Column(name = "cemara_no")
    private String cemaraNo;


    public String getLoadometerNo() {
        return loadometerNo;
    }

    public void setLoadometerNo(String loadometerNo) {
        this.loadometerNo = loadometerNo;
    }

    public String getCemaraNo() {
        return cemaraNo;
    }

    public void setCemaraNo(String cemaraNo) {
        this.cemaraNo = cemaraNo;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }


}
