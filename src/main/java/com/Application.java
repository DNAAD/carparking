package com;


import com.coalvalue.configuration.Docker;
import com.coalvalue.service.MqttService;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication//(scanBasePackages="com")
//@EnableJpaRepositories(repositoryFactoryBeanClass = BaseJpaRepositoryFactoryBean.class)
//@EnableJpaRepositories(repositoryBaseClass = BaseJpaRepositoryImpl.class)
/*@EnableJpaRepositories(basePackages = {"com.coalvalue.domain.entity"},
        repositoryFactoryBeanClass = BaseJpaRepositoryFactoryBean.class//指定自己的工厂类
)*/

//@EnableDiscoveryClient
@EnableScheduling
@EnableJpaAuditing(auditorAwareRef = "jpaAuditorConfiguration")
@ServletComponentScan
@EnableEncryptableProperties
@EnableRetry
//@EnableWebMvc
/*@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@Import({
        DispatcherServletAutoConfiguration.class,
        EmbeddedServletContainerAutoConfiguration.class,
        ErrorMvcAutoConfiguration.class,
        HttpEncodingAutoConfiguration.class,
        HttpMessageConvertersAutoConfiguration.class,
        JacksonAutoConfiguration.class,
        ServerPropertiesAutoConfiguration.class,
        PropertyPlaceholderAutoConfiguration.class,
        ThymeleafAutoConfiguration.class,
        WebMvcAutoConfiguration.class,
        WebSocketAutoConfiguration.class,
})*/
public class Application implements ApplicationListener<ContextRefreshedEvent>
     {

         @Autowired
         private Docker docker;

         /**
          * Start internal H2 server so we can query the DB from IDE
          *
          * @return H2 Server instance
          * @throws SQLException
          */
/*
         @Bean(initMethod = "start", destroyMethod = "stop")
         public Server h2Server() throws SQLException {
             return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
         }
*/

         @Override
        public void onApplicationEvent(ContextRefreshedEvent event) {
//需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
          //  LPRMain lprtest = new LPRMain(plateRecognitionService);
        }

      /*   @Bean
         public UndertowServletWebServerFactory embeddedServletContainerFactory() {
             UndertowServletWebServerFactory factory =
                     new UndertowServletWebServerFactory();

             factory.addBuilderCustomizers(new UndertowBuilderCustomizer() {
                 @Override
                 public void customize(io.undertow.Undertow.Builder builder) {
                     builder.addHttpListener(20001, "0.0.0.0");
                 }
             });

             return factory;
         }*/
/*    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

        return application.sources(Application.class);
    }*/
/*         @Bean
         public SessionRegistry sessionRegistry() {
             return new SessionRegistryImpl();

         }*/
    public static void main(String[] args) {

     //   HardWareCommandService.remoteChrome();

      ApplicationContext ctx = SpringApplication.run(Application.class, args);

   //   ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Application.class).headless(false).run(args);
      String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
        Docker docker = (Docker) ctx.getBean("docker");

        docker.all_together____("");

/*        PlateRecognitionService cgb = (PlateRecognitionService) ctx.getBean("plateRecognitionService");
        LPRMain lprtest = new LPRMain(cgb);*/

        MqttService mqttService = (MqttService) ctx.getBean("mqttService");
       // mqttService.access();

     //   HelloWorldServer helloWorldServer = (HelloWorldServer) ctx.getBean("helloWorldServer");



/*
        try {
            //final HelloWorldServer server = new HelloWorldServer();
            helloWorldServer.start();
            //helloWorldServer.blockUntilShutdown();
        }catch (IOException e){

        }
*/





    /*    ScheduledTasksMqtt scheduledTasksMqtt = (ScheduledTasksMqtt) ctx.getBean("scheduledTasksMqtt");

       scheduledTasksMqtt.init_configuration();*/

        /*catch (InterruptedException e){



        }*/
/*
        QrcodeIPythonService qrcodeIPythonService = (QrcodeIPythonService) ctx.getBean("qrcodeIPythonService");

        qrcodeIPythonService.execute();
        System.out.println("执行任务啊啊啊DDDD"+ totalCharge);

        System.out.println("执行任务啊啊啊DDDroot folder of your current java project"+  System.getProperty("user.dir"));
*/



   /*   WebcamQRCodeExample appFrame = ctx.getBean(WebcamQRCodeExample.class);
        try {
            appFrame.main_();
        } catch (FrameRecorder.Exception e) {
            e.printStackTrace();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
/*        ClassGenerationBean cgb = (ClassGenerationBean) ctx.getBean("classGenerationBean");
        try {
            cgb.generateDomainClass();
            cgb.generateRepositoryClass();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

}
