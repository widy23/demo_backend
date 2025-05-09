package com.example.backend.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootStrapServer;

    private static final String FIRST_TOPIC= "test-topic";
    @Autowired
    private KafkaTemplate<String , String> template;



    public void sendMessage(String username,String idClient,String idProfessional ,String message){
        MessageRequest  messageRequest= new MessageRequest(username,idClient,idProfessional,message);
        template.send(FIRST_TOPIC,messageRequest.getIdReceiver(),message);
    }

}
