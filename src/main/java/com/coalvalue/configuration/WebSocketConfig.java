package com.coalvalue.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    public static String topic__COALPIT_DELIVERY_workbench = "/topic/COALPIT_DELIVERY_workbench/1";
    public static String topic__COALPIT_DELIVERY_report = "/topic/COALPIT_DELIVERY_report/1";

    public static String topic__COALPIT_DELIVERY_scan = "/topic/COALPIT_DELIVERY_scan/";

    public static String topic__COALPIT_DELIVERY_status = "/topic/topic__COALPIT_DELIVERY_status/";
    public static String topic__COALPIT_DELIVERY_auth_bind = "/topic/topic__COALPIT_DELIVERY_auth_bind/";
    public static String topic__COALPIT_DELIVERY_welcome = "/topic/topic__COALPIT_DELIVERY_welcome/";



    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {


        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
        //registry.addEndpoint("/gs-guide-websocket").withSockJS();
    }




}