package com.wangjc.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.wangjc.rabbitmq.*"})
public class WangjcRabbitmqWebtestApplication {

    public static void main(String[] args) {
        SpringApplication.run(WangjcRabbitmqWebtestApplication.class, args);
    }

}
