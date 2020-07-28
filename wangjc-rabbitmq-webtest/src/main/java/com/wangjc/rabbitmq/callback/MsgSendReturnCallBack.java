package com.wangjc.rabbitmq.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 消息发送到交换机失败的确认机制
 * @author wangjc
 * @title: MsgSendReturnCallBack
 * @projectName wangjc-rabbitmq
 * @description: TODO
 */
public class MsgSendReturnCallBack implements RabbitTemplate.ReturnCallback {

    private static final Logger logger = LoggerFactory.getLogger(MsgSendReturnCallBack.class);

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.error("MsgSendReturnCallBack[消息从交换机到队列失败]",message.toString());
        logger.error("消息主体message[{}]",message.toString());
        logger.error("replyCode[{}]",replyCode);
        logger.error("描述[{}]",replyText);
        logger.error("消息使用的交换器 exchange[{}]",exchange);
        logger.error("消息使用的路由键 routing[{}]",routingKey);
    }
}
