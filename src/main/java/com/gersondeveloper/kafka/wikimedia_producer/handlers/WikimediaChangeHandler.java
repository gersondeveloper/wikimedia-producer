package com.gersondeveloper.kafka.wikimedia_producer.handlers;

import com.gersondeveloper.kafka.wikimedia_producer.services.KafkaProducerService;
import com.launchdarkly.shaded.com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.shaded.com.launchdarkly.eventsource.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikimediaChangeHandler implements EventHandler {

    private final String topic;
    private final KafkaProducerService kafkaProducerService;
    private final Logger log = LoggerFactory.getLogger(WikimediaChangeHandler.class.getSimpleName());

    public WikimediaChangeHandler(String topic, KafkaProducerService kafkaProducerService) {
        this.topic = topic;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public void onOpen() {}

    @Override
    public void onClosed() {}

    @Override
    public void onMessage(String s, MessageEvent messageEvent) throws Exception {
        log.info(messageEvent.getData());
        kafkaProducerService.sendMessage(topic, messageEvent.getData(), null);
    }

    @Override
    public void onComment(String s) {}

    @Override
    public void onError(Throwable throwable) {
        log.error("error on stream reading: " + throwable.getMessage());
    }
}