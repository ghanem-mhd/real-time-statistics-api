package com.example.realtimestatisticsapi.transactions;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionsRepositoryTests {

    private TransactionsRepository repository;

    @BeforeEach
    public void initEach() {
        repository = new TransactionsRepository();
    }

    @Test
    public void save_whenCalled_shouldStoreTheTransaction(){
        Transaction transaction = new Transaction(new Date(), BigDecimal.ZERO);
        repository.save(transaction);
        assertEquals(1, repository.getCount(),"repository must contains one transaction");
    }

    @Test
    public void deleteAll_whenCalled_shouldDeleteAllStoredTransactions(){
        Transaction transaction1 = new Transaction(new Date(), BigDecimal.ZERO);
        Transaction transaction2 = new Transaction(new Date(), BigDecimal.ZERO);
        repository.save(transaction1);
        repository.save(transaction2);
        repository.deleteAll();
        assertEquals( 0, repository.getCount(), "repository must be empty");
    }

    @Test
    public void getAll_whenCalled_shouldGetAllStoredTransactions(){
        Transaction transaction1 = new Transaction(new Date(), BigDecimal.ZERO);
        Transaction transaction2 = new Transaction(new Date(), BigDecimal.ZERO);
        repository.save(transaction1);
        repository.save(transaction2);
        assertThat("repository must return 2 transactions",
                repository.getAll(),
                CoreMatchers.hasItems(transaction1, transaction2));
    }

    @AfterEach
    public void tearDownEach(){
        repository = null;
    }
}
