package com.example.realtimestatisticsapi.statistics;

import com.example.realtimestatisticsapi.transactions.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
public class StatisticsServiceTests {

    private StatisticsService service;

    @BeforeEach
    public void initEach() {
        service = new StatisticsService();
    }

    @Test
    void registerTransaction_whenCalledWithTransactionOutsideWindows_shouldBeIgnored() {
        Date outSideWindowDate = getTimestampWithShiftInSeconds(1);
        Transaction transactionInPast = new Transaction(outSideWindowDate, BigDecimal.ZERO);
        service.registerTransaction(transactionInPast);
        assertEquals(0, service.getAggregatedStatistics().getCount());
    }

    @Test
    void registerTransaction_whenCalledWithTransactionInsideWindows_shouldBeConsidered() {
        Transaction transaction = new Transaction(new Date(), new BigDecimal("14.00"));
        service.registerTransaction(transaction);
        Statistics aggregatedStatistics = service.getAggregatedStatistics();
        assertEquals(1, aggregatedStatistics.getCount());
        assertEquals(new BigDecimal("14.00"), aggregatedStatistics.getSum());
        assertEquals(new BigDecimal("14.00"), aggregatedStatistics.getMax());
        assertEquals(new BigDecimal("14.00"), aggregatedStatistics.getMin());
        assertEquals(new BigDecimal("14.00"), aggregatedStatistics.getAvg());
    }

    @Test
    void getStatistics_whenCalledShouldAggregateStatistics() {
        Transaction transaction1 = new Transaction(getTimestampWithShiftInSeconds(-1), new BigDecimal("14.343"));
        Transaction transaction2 = new Transaction(getTimestampWithShiftInSeconds(-2), new BigDecimal("7.30"));
        Transaction transaction3 = new Transaction(getTimestampWithShiftInSeconds(-3), new BigDecimal("1.01"));
        service.registerTransaction(transaction1);
        service.registerTransaction(transaction2);
        service.registerTransaction(transaction3);
        Statistics aggregatedStatistics = service.getAggregatedStatistics();
        assertEquals(3, aggregatedStatistics.getCount());
        assertEquals(new BigDecimal("22.65"), aggregatedStatistics.getSum());
        assertEquals(new BigDecimal("14.34"), aggregatedStatistics.getMax());
        assertEquals(new BigDecimal("1.01"), aggregatedStatistics.getMin());
        assertEquals(new BigDecimal("7.55"), aggregatedStatistics.getAvg());
    }

    @Test
    void refreshStatisticsWindow_whenCalled_shouldClearTheLastSlot() {
        Transaction transaction1 = new Transaction(getTimestampWithShiftInSeconds(-1), new BigDecimal("14.343"));
        service.registerTransaction(transaction1);
        service.refreshStatisticsWindow();
        assertEquals(0, service.statisticsPerSecond.get(service.statisticsPerSecond.size() - 1).getCount());
    }

    @Test
    void refreshStatisticsWindow_whenCalled_shouldShiftSlots() {
        Transaction transaction1 = new Transaction(getTimestampWithShiftInSeconds(-1), new BigDecimal("14.343"));
        Transaction transaction2 = new Transaction(getTimestampWithShiftInSeconds(-2), new BigDecimal("7.30"));
        Transaction transaction3 = new Transaction(getTimestampWithShiftInSeconds(-3), new BigDecimal("1.01"));
        service.registerTransaction(transaction1);
        service.registerTransaction(transaction2);
        service.registerTransaction(transaction3);
        service.refreshStatisticsWindow();
        assertEquals(new BigDecimal("7.30"),
                service.statisticsPerSecond.get(service.statisticsPerSecond.size() - 1).getSum()
        );
        assertEquals(new BigDecimal("1.01"),
                service.statisticsPerSecond.get(service.statisticsPerSecond.size() - 2).getSum()
        );
    }

    private Date getTimestampWithShiftInSeconds(int shift) {
        int shiftInSecond = StatisticsService.STATISTICS_WINDOW_IN_SECONDS + shift;
        return new Date(System.currentTimeMillis() - 1000L * shiftInSecond);
    }
}
