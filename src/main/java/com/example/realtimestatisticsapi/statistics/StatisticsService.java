package com.example.realtimestatisticsapi.statistics;

import com.example.realtimestatisticsapi.transactions.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class StatisticsService {

    protected static final int STATISTICS_WINDOW_IN_SECONDS = 60;

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsService.class);

    protected final List<Statistics> statisticsPerSecond = new ArrayList<>();

    public StatisticsService(){
        IntStream.range(0, STATISTICS_WINDOW_IN_SECONDS)
                .forEach( i -> statisticsPerSecond.add(new Statistics()));
    }

    @Scheduled(fixedRate = 1000)
    protected synchronized void refreshStatisticsWindow() {
        statisticsPerSecond.get(STATISTICS_WINDOW_IN_SECONDS - 1).reset();
        Collections.rotate(statisticsPerSecond, 1);
    }

    public synchronized void registerTransaction(Transaction transaction){
        int transactionAgeInSeconds = (int) transaction.getTransactionAgeInSeconds();
        if (transactionAgeInSeconds > STATISTICS_WINDOW_IN_SECONDS){
            LOGGER.info("{} not registered in statistics service", transaction);
            return;
        }
        statisticsPerSecond.get(transactionAgeInSeconds).update(transaction.getAmount());
        LOGGER.info("{} registered in statistics service in slot {}", transaction, transactionAgeInSeconds);
    }

    public synchronized Statistics getAggregatedStatistics(){
        Statistics aggregatedStatistics = new Statistics();
        for (Statistics s: statisticsPerSecond) {
            aggregatedStatistics.updateCount(s.getCount());
            aggregatedStatistics.updateSum(s.getSum());
            aggregatedStatistics.updateMax(s.getMax());
            aggregatedStatistics.updateMin(s.getMin());
        }
        return aggregatedStatistics;
    }
}
