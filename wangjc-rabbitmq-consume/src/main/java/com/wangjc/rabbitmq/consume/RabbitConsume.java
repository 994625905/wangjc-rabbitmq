package com.wangjc.rabbitmq.consume;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.wangjc.rabbitmq.constant.RabbitMQConstant;
import com.wangjc.rabbitmq.entity.TestLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 消息消费者
 * @author wangjc
 * @title: FirstConsume
 * @projectName wangjc-rabbitmq
 * @description: TODO
 */
@Component
public class RabbitConsume {

    private static final Logger logger = LoggerFactory.getLogger(RabbitConsume.class);

    /**
     * 序列化出消息体，做业务处理,注解声明,
     * 可以标志多个队列名称，这里为了业务区分
     * @param msg
     * @param channel
     * @param tag
     */
    @RabbitListener(queues = {RabbitMQConstant.QUEUE_NAME.FIRST})
    public void handleMessageFirst_one(Message msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag){
        try {
            TestLog log = JSONObject.parseObject(new String(msg.getBody()), TestLog.class);
            logger.info("handleMessageFirst_one,时间戳[{}],操作事项[{}]",log.getCreateTime(),log.getOperate());
            channel.basicAck(tag,false);//手动确认
        } catch (Exception e) {
            logger.error("handleMessageFirst_one消息消费失败，消息体[{}]",new String(msg.getBody()));
            e.printStackTrace();
        }
    }

    /**
     * 测试负载均衡的轮循机制
     * @param msg
     * @param channel
     * @param tag
     */
    @RabbitListener(queues = {RabbitMQConstant.QUEUE_NAME.FIRST})
    public void handleMessageFirst_two(Message msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag){
        try {
            TestLog log = JSONObject.parseObject(new String(msg.getBody()), TestLog.class);
            logger.info("handleMessageFirst_two,时间戳[{}],操作事项[{}]",log.getCreateTime(),log.getOperate());
            channel.basicAck(tag,false);//手动确认
        } catch (Exception e) {
            logger.error("handleMessageFirst_two消息消费失败，消息体[{}]",new String(msg.getBody()));
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = {RabbitMQConstant.QUEUE_NAME.SECOND})
    public void handleMessageSecond(Message msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag){
        try {
            TestLog log = JSONObject.parseObject(new String(msg.getBody()), TestLog.class);
            logger.info("handleMessageSecond,时间戳[{}],操作事项[{}]",log.getCreateTime(),log.getOperate());
            channel.basicAck(tag,false);// 手动确认
//            channel.basicReject(tag,false);//也可以拒绝该消息，消息会被丢弃，不会重回队列
//            手动重新回到队列的代码如下，不手动确认的话，在该节点故障断开后，消息会自动回到队列
//            channel.basicPublish(msg.getMessageProperties().getReceivedExchange(),msg.getMessageProperties().getReceivedRoutingKey(),
//                    MessageProperties.PERSISTENT_TEXT_PLAIN,JSONObject.toJSONString(log).getBytes());
        } catch (IOException e) {
            logger.error("handleMessageSecond消息消费失败，消息体[{}]",new String(msg.getBody()));
            e.printStackTrace();

        }
    }


}
