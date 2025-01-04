package com.gersondeveloper.kafka.wikimedia_producer;

import com.gersondeveloper.kafka.wikimedia_producer.handlers.WikimediaChangeHandler;
import com.gersondeveloper.kafka.wikimedia_producer.services.KafkaProducerService;
import com.launchdarkly.shaded.com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.shaded.com.launchdarkly.eventsource.EventSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Component
public class WikimediaEventSourceRunner {

    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public WikimediaEventSourceRunner(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    public void startEventSource() throws InterruptedException {
        String topic = "wikimedia.recentchange";
        EventHandler eventHandler = new WikimediaChangeHandler(topic, kafkaProducerService);
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";
        EventSource.Builder builder = new EventSource.Builder(eventHandler, URI.create(url));
        EventSource eventSource = builder.build();
        eventSource.start();
        TimeUnit.MINUTES.sleep(1);
    }
}