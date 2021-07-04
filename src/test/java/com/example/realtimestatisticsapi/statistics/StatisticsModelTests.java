package com.example.realtimestatisticsapi.statistics;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StatisticsModelTests {

    private Statistics statistics;
    private final BigDecimal ZERO = new BigDecimal("0.00");

    @BeforeEach
    public void initEach(){
        statistics = new Statistics();
    }

    @Test
    void updateCount_whenCalled_shouldIncrementCountAttributeBy1() {
        statistics.updateCount();
        assertEquals(1, statistics.getCount());
    }

    @Test
    void getSum_whenCalledForEmptyStatistics_shouldReturnZero() {
        assertEquals(ZERO, statistics.getSum());
    }

    @Test
    void getSum_whenCalled_shouldRoundUpToTwoDigits() {
        statistics.setSum(new BigDecimal("100.23624"));
        assertEquals(new BigDecimal("100.24"), statistics.getSum());
    }

    @Test
    void updateSum_whenCalled_shouldAddValueToSumAttribute() {
        statistics.updateSum(new BigDecimal("4.34"));
        statistics.updateSum(new BigDecimal("104.15"));
        assertEquals(new BigDecimal("108.49"), statistics.getSum());
    }

    @Test
    void getMax_whenCalledForEmptyStatistics_shouldReturnNull() {
        assertNull(statistics.getMax());
    }

    @Test
    void getMax_whenCalled_shouldRoundUpToTwoDigits() {
        statistics.setMax(new BigDecimal("10.1"));
        assertEquals(new BigDecimal("10.10"), statistics.getMax());
    }

    @Test
    void updateMax_whenCalledForEmptyStatistics_shouldInitializeMaxAttribute() {
        statistics.updateMax(new BigDecimal("5.5"));
        assertEquals(new BigDecimal("5.50"), statistics.getMax());
    }

    @Test
    void updateMax_whenCalledWithBiggerValue_shouldReplaceTheMaxAttribute() {
        statistics.setMax(new BigDecimal("15"));
        statistics.updateMax(new BigDecimal("15.5"));
        assertEquals(new BigDecimal("15.50"), statistics.getMax());
    }

    @Test
    void getMin_whenCalledForEmptyStatistics_shouldReturnZero() {
        assertNull(statistics.getMin());
    }

    @Test
    void getMin_whenCalled_shouldRoundUpToTwoDigits() {
        statistics.setMin(new BigDecimal("10.1"));
        assertEquals(new BigDecimal("10.10"), statistics.getMin());
    }

    @Test
    void updateMin_whenCalledForEmptyStatistics_shouldInitializeMinAttribute() {
        statistics.updateMin(new BigDecimal("5.5"));
        assertEquals(new BigDecimal("5.50"), statistics.getMin());
    }

    @Test
    void updateMin_whenCalledWithLowerValue_shouldReplaceTheMinAttribute() {
        statistics.setMin(new BigDecimal("15"));
        statistics.updateMin(new BigDecimal("14"));
        assertEquals(new BigDecimal("14.00"), statistics.getMin());
    }

    @Test
    void update_whenCalled_shouldUpdateCountSumMaxMinAttributes() {
        statistics.update(new BigDecimal("10.50"));
        statistics.update(new BigDecimal("7.00"));
        assertEquals(2, statistics.getCount());
        assertEquals(new BigDecimal("17.50"), statistics.getSum());
        assertEquals(new BigDecimal("10.50"), statistics.getMax());
        assertEquals(new BigDecimal("7.00"), statistics.getMin());
        assertEquals(new BigDecimal("8.75"), statistics.getAvg());
    }

    @Test
    void reset_whenCalled_shouldUpdateCountSumMaxMinAttributes() {
        statistics.update(new BigDecimal("10.50"));
        statistics.update(new BigDecimal("7.00"));
        statistics.reset();
        assertEquals(0, statistics.getCount());
        assertEquals(ZERO, statistics.getSum());
        assertNull(statistics.getMax());
        assertNull(statistics.getMin());
        assertEquals(ZERO, statistics.getAvg());
    }

    @Test
    void getAvg_whenCalledForEmptyStatistics_shouldReturnZero() {
        assertEquals(ZERO, statistics.getAvg());
    }

    @Test
    void getAvg_whenCalled_shouldCalculateTheAverageFromCountSumAttributes() {
        statistics.setCount(10);
        statistics.setSum(new BigDecimal("10"));
        assertEquals(new BigDecimal("1.00"), statistics.getAvg());
    }

    @AfterEach
    public void tearDownEach(){
        statistics = null;
    }
}
