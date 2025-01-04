    package com.gersondeveloper.kafka.wikimedia_producer.services;

import jakarta.annotation.Nullable;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaProducer<String, String> kafkaProducer;
    private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class.getSimpleName());

    @Autowired
    public KafkaProducerService(KafkaProducer<String, String> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;

    }

    public void sendMessage(String topic, String value, @Nullable String key) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
        kafkaProducer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception e) {
                if(e == null){
                    log.info("Received new metadata \nTopic: {}\nPartition: {}\nTimestamp: {}\nOffset: {}\nKey: {}\n", metadata.topic(), metadata.partition(), metadata.timestamp(), metadata.offset(), key);
                } else {
                    log.error("Error producing message!{}", e.getMessage());
                }
            }
        });
    }
}
