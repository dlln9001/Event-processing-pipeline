package com.event_processing.worker.transactions;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import tools.jackson.databind.ObjectMapper;

// listens to kafka, getting transaction events to be eventually stored in db
@Component
public class TransactionsConsumer {

    private final ObjectMapper objectMapper;
    private final TransactionsService service;

    public TransactionsConsumer(ObjectMapper objectMapper, TransactionsService service) {
        this.objectMapper = objectMapper;
        this.service = service;
    }

    @KafkaListener(id = "transaction-group", topics = "topic-A")
    public void listen(String message) {
        System.out.println("Received message: " + message);

        Transaction transaction = objectMapper.readValue(message, Transaction.class);

        this.service.saveTransaction(transaction);

    }
}
