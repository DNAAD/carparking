package com.coalvalue.notification.liveEvent;


import com.coalvalue.service.DeliveryOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EventObject;


public class BarDecodedCommandE extends EventObject {



    private String type;
    private String data;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public BarDecodedCommandE(Object source) {
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