package com.example.backend.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaTopicListener {

    private static final String TOPIC_NAME = "test-topic";
    private static final String GROUP_ID = "mi-grupo-consumidores";

    @KafkaListener(topics = TOPIC_NAME, groupId = GROUP_ID)
    public void escucharMensaje(String mensaje) {
        System.out.println(String.format("Mensaje recibido: %s", mensaje));
    }
}
