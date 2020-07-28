package com.wangjc.rabbitmq.core;

import com.alibaba.fastjson.JSON;
import com.wangjc.rabbitmq.constant.RabbitMQConstant;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 消息发送的core
 * @author wangjc
 * @title: MsgSendCore
 * @projectName wangjc-rabbitmq
 * @description: TODO
 */
@Component
public class MsgSendCore {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息，first队列
     * @param obj
     */
    public void sendByFirst(Object obj){
        /**
         * 创建correlationData，做rabbitmq的可靠投递依据
         */
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        /**
         * 创建消息体
         */
        Message message = MessageBuilder.withBody(JSON.toJSONString(obj).getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON).setCorrelationId(correlationData.getId()).build();
        // 发送
        rabbitTemplate.convertAndSend(RabbitMQConstant.EXCHANGE.DIRECT_ONE,RabbitMQConstant.ROUTING.FIRST,message,correlationData);
    }

    /**
     * 发送消息，second队列
     * @param obj
     */
    public void sendBySecond(Object obj){
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        Message message = MessageBuilder.withBody(JSON.toJSONString(obj).getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON).setCorrelationId(correlationData.getId()).build();
        // 发送
        rabbitTemplate.convertAndSend(RabbitMQConstant.EXCHANGE.DIRECT_ONE,RabbitMQConstant.ROUTING.SECOND,message,correlationData);
    }

}
