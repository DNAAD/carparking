package com.coalvalue.task;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.List;

@Component
public class UpdateShedule  {
/*
    @Autowired
    private UpdateFX updater;*/


 //   @Scheduled(fixedDelay = 10000,   initialDelay=3000)
    public void valueBound() {

        System.out.println("------------------------检查是否有更新");
      //  updater.checkUpdates();
    }
 


}