package com.example.realtimestatisticsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class RealTimeStatisticsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RealTimeStatisticsApiApplication.class, args);
    }

}