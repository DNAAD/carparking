package com.coalvalue.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfgurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketMessageBrokerConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
        //registry.addEndpoint("/hello").setAllowedOrigins("*");
      //  registry.addEndpoint("/hello").withSockJS();

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
       registry.enableSimpleBroker("/topic");
    //   registry.enableStompBrokerRelay("/topic", "/queue");//.setRelayHost("vm://localhost").setRelayPort(0)
           //     .setSystemHeartbeatReceiveInterval(20000)
      //  .setSystemHeartbeatReceiveInterval(20000);

/*        .setRelayHost("localhost")
                .setRelayPort(61613)
                .setClientLogin("system")
                .setClientPasscode("password");*/
        registry.setApplicationDestinationPrefixes("/app");
    }




    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }





}