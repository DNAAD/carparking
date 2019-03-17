package com.coalvalue.update;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import org.apache.commons.lang.StringUtils;

import java.util.Properties;

/**
 * Factory for creating {@link DockerClient}s
 */
public class DockerClientFactory {
 
    private static DockerClient dockerClient;
 
    public static DockerClient build() {
        if (dockerClient == null) {



            System.out.println("----------------我梦在docker中啊啊啊啊啊啊 ");
            Properties properties = new Properties();
            // properties.setProperty("registry.email", "info@baeldung.com");
            //   properties.setProperty("registry.password", "baeldung");
            //    properties.setProperty("registry.username", "baaldung");
            //  properties.setProperty("DOCKER_CERT_PATH", "/home/baeldung/.docker/certs");
            //   properties.setProperty("DOCKER_CONFIG", "/home/baeldung/.docker/");
            //  properties.setProperty("DOCKER_TLS_VERIFY", "1");
            properties.setProperty("DOCKER_HOST", "");//unix:///var/run/docker.sock");//
            //   tcp://localhost:2375

            DefaultDockerClientConfig config
                    = DefaultDockerClientConfig.createDefaultConfigBuilder()
                    .withProperties(properties).build();

            DockerClient dockerClient = null;
            if(""!= null){
                dockerClient = DockerClientBuilder.getInstance(config).build();

            }else{
                dockerClient = DockerClientBuilder.getInstance().build();

            }




        }
        return dockerClient;
    }
 
}