package com.coalvalue.service.assistant;

import com.coalvalue.service.strategy.StrategyService;
import io.moquette.BrokerConstants;
import io.moquette.broker.Server;
import io.moquette.broker.config.IConfig;
import io.moquette.broker.config.MemoryConfig;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import io.moquette.interception.InterceptHandler;



import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Properties;


//@Configuration

public class ModuleMqttBrokerConfig {



    private String broker;

    @Value("${own.configuration.mqtt.local-broker.port}")
    private Integer BROKER_PORT;
    @Value("${own.configuration.mqtt.local-broker.url}")
    private String BROKER_HOST;


    @Autowired
    PublisherListener publisherListener;
/*
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        final IConfig classPathConfig = new ClasspathConfig();

        final Server mqttBroker = new Server();
        final List<? extends InterceptHandler> userHandlers = Arrays.asList(new PublisherListener());
        try {
            mqttBroker.startServer(classPathConfig, userHandlers);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("moquette mqtt broker started, press ctrl-c to shutdown..");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("stopping moquette mqtt broker..");
                mqttBroker.stopServer();
                System.out.println("moquette mqtt broker stopped");
            }
        });
    }

*/




    @Bean
    public Server broker() {

        // load topics from manifest file
        //loadTopics();

        Server mqttBroker = new Server();
        Properties props = new Properties();

        // get properties from manifest file
        props.setProperty(BrokerConstants.PORT_PROPERTY_NAME, Integer.toString(BROKER_PORT));
        props.setProperty(BrokerConstants.HOST_PROPERTY_NAME, BROKER_HOST);

        //   props.setProperty(BrokerConstants.PASSWORD_FILE_PROPERTY_NAME, Info.PATHS.PATH_DEVICES_FOLDER + "/mqtt-broker/config/password_file.conf");
        //  props.setProperty(BrokerConstants.PERSISTENT_STORE_PROPERTY_NAME, Info.PATHS.PATH_DEVICES_FOLDER + "/mqtt-broker/config/moquette_store.mapdb");

        List<? extends InterceptHandler> userHandlers = Collections.singletonList(publisherListener);

        IConfig config = new MemoryConfig(props);

        try {
            mqttBroker.startServer(config, userHandlers);
        } catch (IOException ex) {
            ex.printStackTrace();// throw new PluginStartupException("Plugin can't start for an IOException.", ex);
        }

        System.out.println("moquette mqtt broker started, press ctrl-c to shutdown..");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("stopping moquette mqtt broker..");
                mqttBroker.stopServer();
                System.out.println("moquette mqtt broker stopped");
            }
        });

        return mqttBroker;
        // setDescription("MQTT broker listening to " + config.getProperty(BrokerConstants.HOST_PROPERTY_NAME) + ":" + config.getProperty(BrokerConstants.PORT_PROPERTY_NAME));
        //LOG.info("MQTT broker plugin started");
    }

    private static final String clientid = "server111";
    private String userName = "test";
    private String passWord = "test";
    public static final String TOPIC = "house/bulbs/bulb1";



    private MqttTopic topic;
    public static final String HOST = "tcp://localhost:11000";
    @Bean
    @DependsOn({"broker"})
    @Qualifier("embeddedBroker")
    public MqttClient client__() throws MqttException {
        MqttClient client= new MqttClient(HOST, clientid, new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(userName);
        options.setPassword(passWord.toCharArray());
        // 设置超时时间
        options.setConnectionTimeout(10);
        // 设置会话心跳时间
        options.setKeepAliveInterval(20);
        try {
            client.setCallback(new ModuleMqttClientConfig.PushCallback());
            client.connect(options);
            topic = client.getTopic(TOPIC);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("------命令 mqttclient 成功 连接 ratained状态");


        return client;
    }


    public void publish(MqttMessage message) throws MqttPersistenceException, MqttException {
        MqttDeliveryToken token = topic.publish(message);
        token.waitForCompletion();
        System.out.println(token.isComplete() + "========");
    }








    public static void main(String[] args) {
        Integer BROKER_PORT = 11000;
        String BROKER_HOST = "localhost";
        // load topics from manifest file
        //loadTopics();

        Server mqttBroker = new Server();
        Properties props = new Properties();

        // get properties from manifest file
        props.setProperty(BrokerConstants.PORT_PROPERTY_NAME, Integer.toString(BROKER_PORT));
        props.setProperty(BrokerConstants.HOST_PROPERTY_NAME, BROKER_HOST);

     //   props.setProperty(BrokerConstants.PASSWORD_FILE_PROPERTY_NAME, Info.PATHS.PATH_DEVICES_FOLDER + "/mqtt-broker/config/password_file.conf");
      //  props.setProperty(BrokerConstants.PERSISTENT_STORE_PROPERTY_NAME, Info.PATHS.PATH_DEVICES_FOLDER + "/mqtt-broker/config/moquette_store.mapdb");

        List<? extends InterceptHandler> userHandlers = null;//Collections.singletonList(publisherListener());

        IConfig config = new MemoryConfig(props);

        try {
            mqttBroker.startServer(config, userHandlers);
        } catch (IOException ex) {
           ex.printStackTrace();// throw new PluginStartupException("Plugin can't start for an IOException.", ex);
        }

        System.out.println("moquette mqtt broker started, press ctrl-c to shutdown..");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("stopping moquette mqtt broker..");
                mqttBroker.stopServer();
                System.out.println("moquette mqtt broker stopped");
            }
        });

    }
}