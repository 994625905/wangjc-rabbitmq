package com.wangjc.rabbitmq.config;

import com.wangjc.rabbitmq.constant.RabbitMQConstant;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 队列配置，可以配置多个队列
 * @author wangjc
 * @title: QueueConfig
 * @projectName wangjc-rabbitmq
 * @description: TODO
 */
@Configuration
public class QueueConfig {

    @Bean
    public Queue firstQueue() {
        /**
         * durable="true" 持久化 rabbitmq重启的时候不需要创建新的队列
         * exclusive  表示该消息队列是否只在当前connection生效,默认是false
         * auto-delete 表示消息队列没有在使用时将被自动删除 默认是false
         */
        return new Queue(RabbitMQConstant.QUEUE_NAME.FIRST,true,false,false);
    }

    @Bean
    public Queue secondQueue() {
        return new Queue(RabbitMQConstant.QUEUE_NAME.SECOND,true,false,false);
    }

}
