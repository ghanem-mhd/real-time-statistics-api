package com.example.realtimestatisticsapi.transactions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionsServiceTests {

    private TransactionsService service;

    @BeforeEach
    public void initEach() {
        service = new TransactionsService();
    }

    @Test
    public void isTransactionOld_whenCalledForNullTransaction_shouldReturnFalse(){
        assertFalse(service.isTransactionOld(null));
    }

    @Test
    public void isTransactionOld_whenCalledForTransactionWithNullTimestamp_shouldReturnFalse(){
        assertFalse(service.isTransactionOld(new Transaction(null, null)));
    }

    @Test
    public void isTransactionOld_whenCalledForTransactionWithTimestampInLast60Seconds_shouldReturnFalse(){
        Transaction transaction = new Transaction(new Date(), BigDecimal.ZERO);
        assertFalse(service.isTransactionOld(transaction));
    }

    @Test
    public void isTransactionOld_whenCalledForTransactionWithTimestampOlderThan60Seconds_shouldReturnTrue(){
        Transaction transaction = new Transaction(new Date(System.currentTimeMillis() - 1000 * 60), null);
        assertTrue(service.isTransactionOld(transaction));
    }

    @AfterEach
    public void tearDownEach(){
        service = null;
    }
}
