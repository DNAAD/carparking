package com.coalvalue.model;

public class TruckComming {

    private String enteringOrExiting;

    private String lisence;
    public Integer nDirection;
    public byte[] license;
    public Integer nColor;
    public String path;


    public String getEnteringOrExiting() {
        return enteringOrExiting;
    }

    public void setEnteringOrExiting(String enteringOrExiting) {
        this.enteringOrExiting = enteringOrExiting;
    }

    public String getLisence() {
        return lisence;
    }

    public void setLisence(String lisence) {
        this.lisence = lisence;
    }
}