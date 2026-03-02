package com.event_processing.worker.transactions;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionsService {

    private final TransactionsRepository repository;

    public TransactionsService(TransactionsRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void saveTransaction(Transaction transaction) {
        repository.save(transaction);

        System.out.println("Saved to database: " + transaction.getId());

    }

}