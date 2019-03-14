package com.coalvalue.service;
//https://medium.com/@bdarfler/synchronous-request-response-with-activemq-and-spring-21359a438a86
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

//@Service
public class PropertyServiceForJasyptStarter {
 
    @Value("${encrypted.property}")
    private String property;
 
    public String getProperty() {
        return property;
    }
 
    public String getPasswordUsingEnvironment(Environment environment) {
        return environment.getProperty("encrypted.property");
    }
}