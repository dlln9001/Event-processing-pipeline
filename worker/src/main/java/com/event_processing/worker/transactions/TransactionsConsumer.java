package com.event_processing.worker.transactions;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


// listens to kafka, getting transaction events to be eventually stored in db
@Component
public class TransactionsConsumer {

    @KafkaListener(id = "transaction-group", topics = "topic-A")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}