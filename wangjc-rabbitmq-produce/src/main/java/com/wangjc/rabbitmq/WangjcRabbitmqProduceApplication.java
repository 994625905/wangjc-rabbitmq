package com.wangjc.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 该模块不用启动，只是之前抽出提供rabbitmqTemplate的
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.wangjc.rabbitmq.*"})
public class WangjcRabbitmqProduceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WangjcRabbitmqProduceApplication.class, args);
    }

}
