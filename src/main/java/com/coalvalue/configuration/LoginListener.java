package com.coalvalue.configuration;

import com.coalvalue.domain.entity.User;
import com.coalvalue.task.LiveBroadcast;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class LoginListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    LiveBroadcast liveBroadcast;
    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event)
    {logger.info("LOGIN name: "+event.getSource());

        User user = (User) event.getAuthentication().getPrincipal();
        System.out.println("LOGIN name: "+user.getUsername());
        try {
            liveBroadcast.reportPrincipalEvent(user);
        } catch (MqttException e) {
            e.printStackTrace();
            logger.error("无法报告 登录事件");
        }
        ;

    }


}