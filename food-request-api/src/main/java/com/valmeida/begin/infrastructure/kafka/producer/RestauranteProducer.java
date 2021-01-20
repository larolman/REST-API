package com.valmeida.begin.infrastructure.kafka.producer;

import java.util.UUID;

import com.valmeida.begin.RestauranteAvro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

public class RestauranteProducer {

    @Autowired
    private KafkaTemplate<String, RestauranteAvro> kafkaTemplate;

    @Value("${kafka.topic}")
    private String topic;

    public void send(RestauranteAvro message) {
        final var messageKey = UUID.randomUUID().toString();
        this.kafkaTemplate.send(this.topic, messageKey, message);
    }
}
