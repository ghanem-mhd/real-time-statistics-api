package com.example.realtimestatisticsapi.transactions;

public class TransactionInPastException extends Exception {

    public TransactionInPastException(String message) {
        super(message);
    }
}
