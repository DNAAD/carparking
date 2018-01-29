package com.coalvalue.web.valid;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;

/**
 * Created by silence on 2016/1/23.
 */
public class InstanceTransportCreateForm {


    private String lineName;

    private Integer departureStation;

    private Integer arrivalStation;

    private String description;

    private String status;
    private Integer id;
    private BigDecimal tareWeight;

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public Integer getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(Integer departureStation) {
        this.departureStation = departureStation;
    }

    public Integer getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(Integer arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public BigDecimal getTareWeight() {
        return tareWeight;
    }

    public void setTareWeight(BigDecimal tareWeight) {
        this.tareWeight = tareWeight;
    }
}
