package com.coalvalue.notification.liveEvent;


import java.util.EventObject;


public class ObjectedDecodedCommandE extends EventObject {



    private String type;
    private String data;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ObjectedDecodedCommandE(Object source) {
        super(source);
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
};