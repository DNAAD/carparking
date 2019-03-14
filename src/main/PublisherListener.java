package com.coalvalue.service.assistant;

import com.coalvalue.service.strategy.StrategyService;
import io.moquette.interception.AbstractInterceptHandler;
import io.moquette.interception.messages.InterceptPublishMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import static io.moquette.broker.Utils.readBytesAndRewind;

@Component
class PublisherListener extends AbstractInterceptHandler {
        @Override
        public String getID() {
            return "556";
        }

    @Autowired
            @Lazy
    StrategyService strategyService;
        @Override
        public void onPublish(InterceptPublishMessage message) {

          //  message.getClientID();
            System.out.println("moquette mqtt broker message intercepted, topic: " + message.getClientID());//
            System.out.println("moquette mqtt broker message intercepted, topic: " + message.getUsername());//

            System.out.println("moquette mqtt broker message intercepted, topic: " +  new String(readBytesAndRewind(message.getPayload())));//


            System.out.println("moquette mqtt broker message intercepted, topic: " + message.getTopicName());//


            if(message.getTopicName().equals("health")){
                System.out.println("HEALTH: " + message.getTopicName());//

                strategyService.health(new String(readBytesAndRewind(message.getPayload())).split(",")[1]);
            }
            
             //       + ", content: " + new String(message.getPayload().array()));
        }
    }