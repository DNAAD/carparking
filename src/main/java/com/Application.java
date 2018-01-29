package com;

import com.coalvalue.qrcode.WebcamQRCodeExample;

import com.coalvalue.repository.base.BaseJpaRepositoryFactoryBean;
import com.coalvalue.service.MqttService;
import com.coalvalue.service.PlateRecognitionService;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = BaseJpaRepositoryFactoryBean.class)
//@EnableJpaRepositories(repositoryBaseClass = BaseJpaRepositoryImpl.class)
/*@EnableJpaRepositories(basePackages = {"com.coalvalue.domain.entity"},
        repositoryFactoryBeanClass = BaseJpaRepositoryFactoryBean.class//指定自己的工厂类
)*/

//@EnableDiscoveryClient
@EnableJpaAuditing(auditorAwareRef = "jpaAuditorConfiguration")
public class Application extends SpringBootServletInitializer implements ApplicationListener<ContextRefreshedEvent>
     {
         @Autowired
         private PlateRecognitionService plateRecognitionService;

         @Override
        public void onApplicationEvent(ContextRefreshedEvent event) {
//需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
          //  LPRMain lprtest = new LPRMain(plateRecognitionService);
        }


    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

        return application.sources(Application.class);
    }

    public static void main(String[] args) {
      // ApplicationContext ctx = SpringApplication.run(Application.class, args);

      ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Application.class).headless(false).run(args);
      String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }

/*        PlateRecognitionService cgb = (PlateRecognitionService) ctx.getBean("plateRecognitionService");
        LPRMain lprtest = new LPRMain(cgb);
*/
        MqttService mqttService = (MqttService) ctx.getBean("mqttService");
        mqttService.access();



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
