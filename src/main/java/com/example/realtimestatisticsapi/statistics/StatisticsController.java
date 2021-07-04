package com.example.realtimestatisticsapi.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/statistics")
@RestController
public class StatisticsController {

    @Autowired
    public StatisticsService statisticsService;

    @RequestMapping(method = RequestMethod.GET)
    public Statistics getTransactions() {
        return statisticsService.getAggregatedStatistics();
    }
}
