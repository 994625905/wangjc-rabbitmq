package com.wangjc.rabbitmq.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 消息发送到交换机的确认机制
 * @author wangjc
 * @title: MsgSendConfirmCallBack
 * @projectName wangjc-rabbitmq
 * @description: TODO
 * @date 2020/7/2513:26
 */
public class MsgSendConfirmCallBack implements RabbitTemplate.ConfirmCallback {

    private static final Logger logger = LoggerFactory.getLogger(MsgSendConfirmCallBack.class);

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info("MsgSendConfirmCallBack[消息发送交换机成功]，回调id[{}],消息体[{}]",correlationData.getId(),correlationData.getReturnedMessage().toString());
        if(ack){
            logger.info("消息发送成功,原因[{}]",cause);
        }else{
            logger.info("消息发送失败，原因[{}]",cause);
        }
    }
}
