package com.example.realtimestatisticsapi.transactions;

import org.springframework.data.repository.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@org.springframework.stereotype.Repository
public class TransactionsRepository implements Repository<Transaction, UUID> {

    private final Map<UUID, Transaction> transactions = new ConcurrentHashMap<>();

    public void save(Transaction transaction) {
        transactions.put(transaction.getId(), transaction);
    }

    public void deleteAll() {
        transactions.clear();
    }

    public Collection<Transaction> getAll() {
        return transactions.values();
    }

    public long getCount() {
        return transactions.size();
    }
}
