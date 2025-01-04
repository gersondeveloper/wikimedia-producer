package com.gersondeveloper.kafka.wikimedia_producer.configuration;

import jakarta.annotation.PreDestroy;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaProducerConfiguration {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.acks}")
    private String acks;

    @Value("${spring.kafka.retries}")
    private int retries;

    private KafkaProducer<String, String> kafkaProducer;

    @Bean
    public KafkaProducer<String, String> kafkaProducer(){
        if(kafkaProducer == null) {
            Properties props = new Properties();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            props.put(ProducerConfig.ACKS_CONFIG, acks);
            props.put(ProducerConfig.RETRIES_CONFIG, retries);
            kafkaProducer = new KafkaProducer<>(props);
        }
        return kafkaProducer;
    }

    @PreDestroy
    public void closeProducer(){
        if (kafkaProducer != null){
            kafkaProducer.flush();
            kafkaProducer.close();
        }
    }

}
