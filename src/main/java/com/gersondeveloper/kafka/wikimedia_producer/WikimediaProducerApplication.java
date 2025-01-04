package com.gersondeveloper.kafka.wikimedia_producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class WikimediaProducerApplication {

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext context = SpringApplication.run(WikimediaProducerApplication.class, args);
		WikimediaEventSourceRunner runner = context.getBean(WikimediaEventSourceRunner.class);
		runner.startEventSource();
	}
}