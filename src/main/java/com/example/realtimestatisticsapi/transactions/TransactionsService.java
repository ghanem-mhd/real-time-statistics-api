package com.example.realtimestatisticsapi.transactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;

@Service
public class TransactionsService {

    private static final int TRANSACTION_AGE_IN_SECONDS = 60;

    @Autowired
    TransactionsRepository repository;

    public Transaction saveTransaction(Transaction transaction){
        return repository.save(transaction);
    }

    public Collection<Transaction> getAllTransaction() {
        return repository.getAll();
    }

    public boolean isTransactionOld(Transaction transaction){
        if (transaction == null || transaction.getTimestamp() == null ){
            return false;
        }
        return Instant.now().minusSeconds(TRANSACTION_AGE_IN_SECONDS).isAfter(transaction.getTimestamp().toInstant());
    }

    public void deleteAllTransactions(){
        repository.deleteAll();
    }
}
