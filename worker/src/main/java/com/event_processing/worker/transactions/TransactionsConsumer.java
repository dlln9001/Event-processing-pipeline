package com.event_processing.worker.transactions;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import tools.jackson.databind.ObjectMapper;

// listens to kafka, getting transaction events to be eventually stored in db
@Component
public class TransactionsConsumer {

    private final ObjectMapper objectMapper;

    public TransactionsConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(id = "transaction-group", topics = "topic-A")
    public void listen(String message) {
        try {
            System.out.println("Received message: " + message);

            Transaction transaction = objectMapper.readValue(message, Transaction.class);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
