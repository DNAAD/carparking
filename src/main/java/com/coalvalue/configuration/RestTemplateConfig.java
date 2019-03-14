package com.coalvalue.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

/**
 * 定义restTemplate的配置
 * 
 * @author wenbronk
 * @Date 下午4:33:35
 */
//@Configuration
public class RestTemplateConfig {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    //在配置类中使用restTemplateBuilder构建RestTemplate实例
    @Bean
    public RestTemplate restTemplate() {
        return restTemplateBuilder.build();
    }




/*
    @Bean
    public RestTemplate restTemplate() {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(new FileInputStream(new File("keystore.jks")),
                "secret".toCharArray());
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
                new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy())
                        .loadKeyMaterial(keyStore, "password".toCharArray()).build());
        HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
                httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        ResponseEntity<String> response = restTemplate.getForEntity(
                "https://localhost:8443", String.class);
    }
*/

}
