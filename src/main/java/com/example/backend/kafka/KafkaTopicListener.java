package com.example.backend.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

@Component
public class KafkaListener {

    private static final String TOPIC_NAME = "mi-primer-topic";
    private static final String GROUP_ID = "mi-grupo-consumidores";
    public void listenMessage(String message){}
}
