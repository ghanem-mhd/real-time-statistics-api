package com.example.realtimestatisticsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class RealTimeStatisticsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RealTimeStatisticsApiApplication.class, args);
    }

}

@RestController
class HelloWorldController {
    @RequestMapping("/")
    public String hello() {
        return "Hello World!";
    }
}
