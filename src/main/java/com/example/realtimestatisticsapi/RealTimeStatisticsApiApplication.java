package com.example.realtimestatisticsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableScheduling
public class RealTimeStatisticsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RealTimeStatisticsApiApplication.class, args);
    }

}

@RestController
class IndexController {
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping("/")
    public String index() {
        return "Ok!";
    }
}