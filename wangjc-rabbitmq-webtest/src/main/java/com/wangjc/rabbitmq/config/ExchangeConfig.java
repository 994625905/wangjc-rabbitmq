package com.wangjc.rabbitmq.config;

import com.wangjc.rabbitmq.constant.RabbitMQConstant;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息交换机配置  可以配置多个
 * @author wangjc
 * @title: ExchangeConfig
 * @projectName wangjc-rabbitmq
 * @description: TODO
 */
@Configuration
public class ExchangeConfig {

    /**
     *   1.定义direct exchange，绑定queueTest
     *   2.durable=true rabbitmq重启的时候不需要创建新的交换机--持久化
     *     autoDelete=false 删除标志，当所有队列在完成使用此exchange时，不删除
     */
    @Bean
    public DirectExchange directExchange(){
        DirectExchange directExchange = new DirectExchange(RabbitMQConstant.EXCHANGE.DIRECT_ONE,true,false);
        return directExchange;
    }
}
