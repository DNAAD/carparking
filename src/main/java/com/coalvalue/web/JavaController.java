package com.coalvalue.web;



import com.coalvalue.configuration.MqttPublishSample;
import com.coalvalue.domain.OperationResult;
import com.coalvalue.repository.EquipmentRepository;
import com.coalvalue.service.strategy.StrategyService;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.*;

@RestController
public class JavaController {

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private MqttPublishSample mqttPublishSample;
    @Autowired
    private MqttClient mqttClient;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private StrategyService strategyService;

    @Autowired
    private EquipmentRepository equipmentRepository;
    @RequestMapping("/java-user")
    public String JavaUser() {
        return "{'username': 'java', 'password': 'java'}"  ;
    }
    @RequestMapping("/python-user")
    public String PythonUser() {
        return restTemplate.getForEntity("http://py-sidecar/getUser", String.class).getBody();
    }



















    @PostMapping("/upload_")
    public ResponseEntity<String> upload_(@RequestParam("file") MultipartFile multiPartFileList, @RequestParam Map data){
        System.out.println("---------- upload file" + data.toString());

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>();
        try {
           System.out.println( new String(multiPartFileList.getBytes()));

            ByteArrayResource resource = new ByteArrayResource(multiPartFileList.getBytes()) {
            @Override
            public String getFilename() {
                return multiPartFileList.getOriginalFilename();
            }
        };


            body.add("file", resource);
          //  body.add("file", getTestFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);


        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        String serverUrl = "http://192.168.10.109:3000/upload";
        // RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
        System.out.println("Response code: " + response.getBody());
        return response;
    }





    @PostMapping("/upload")
    public ResponseEntity upload(@RequestParam("file") MultipartFile multiPartFileList, @RequestParam Map data){
        System.out.println("---------- upload file"+data.toString());


        OperationResult operationResult = strategyService.receive(data,multiPartFileList);
        if(operationResult.isSuccess()){
            return ResponseEntity.ok("");
        }

        return ResponseEntity.of(Optional.empty());

    }



/*    @GetMapping("/videos/{name}/full")
    ResponseEntity getFullVideo(@PathVariable  String name): ResponseEntity<UrlResource> {
        UrlResource video = new UrlResource("file:$videoLocation/$name");
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaTypeFactory
                        .getMediaType(video)
                        .orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(video);
    }*/
}

