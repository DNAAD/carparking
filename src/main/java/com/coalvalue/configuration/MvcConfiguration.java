package com.coalvalue.configuration;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
/*import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;*/
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Page;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;


/**
 * Created by zhongkw on 1/5/2015.
 */
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @Value("${deploy.resource.path}")
    private String resource_path;



/*
@Autowired(required = true)
public void configeJackson(Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder) {
    jackson2ObjectMapperBuilder.serializationInclusion(JsonInclude.Include.NON_NULL);
}
*/



/*
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("*/
/**")
                .addResourceLocations("/static/").addResourceLocations("/WEB-INF/")
          //      .addResourceLocations("/WEB-INF/files*//*
)
                .addResourceLocations("./files/")
              .addResourceLocations("classpath:/templates/");
    }
*/

@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
        .addResourceLocations("/WEB-INF/")
                .addResourceLocations("file:"+resource_path+"/")
                .addResourceLocations("./files/");
    }







    @Bean
    @ConditionalOnMissingBean(ClassPathTldsLoader.class)
    public ClassPathTldsLoader classPathTldsLoader(){
        return new ClassPathTldsLoader();
    }

}

