package com.event_processing.worker.transactions;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.event_processing.worker.transactions.Transaction;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Long>{

}