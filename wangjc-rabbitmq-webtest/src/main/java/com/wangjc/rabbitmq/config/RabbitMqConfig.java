package com.wangjc.rabbitmq.config;

import com.wangjc.rabbitmq.callback.MsgSendConfirmCallBack;
import com.wangjc.rabbitmq.callback.MsgSendReturnCallBack;
import com.wangjc.rabbitmq.constant.RabbitMQConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitmq的配置
 * @author wangjc
 * @title: RabbitConfig
 * @projectName wangjc-rabbitmq
 * @description: TODO
 * @date 2020/7/2514:13
 */
@Configuration
public class RabbitMqConfig {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqConfig.class);

    /** 队列 */
    @Autowired
    private QueueConfig queueConfig;

    /** 交换机 */
    @Autowired
    private ExchangeConfig exchangeConfig;

    /** 连接工厂 */
    @Autowired
    private ConnectionFactory connectionFactory;

    /**
     * 将消息队列1和交换机进行绑定
     * @return
     */
    @Bean
    public Binding bindingOne(){
        return BindingBuilder.bind(queueConfig.firstQueue()).to(exchangeConfig.directExchange()).with(RabbitMQConstant.ROUTING.FIRST);
    }

    /**
     * 将消息队列2和交换机进行绑定
     * @return
     */
    @Bean
    public Binding bindingTwo() {
        return BindingBuilder.bind(queueConfig.secondQueue()).to(exchangeConfig.directExchange()).with(RabbitMQConstant.ROUTING.SECOND);
    }

    /**
     * 队列listener  观察 监听模式
     * 当有消息到达时会通知监听在对应的队列上的监听对象
     * @return
     */
//    @Bean
//    public SimpleMessageListenerContainer simpleMessageListenerContainer_one(){
//        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);
//        simpleMessageListenerContainer.addQueues(queueConfig.firstQueue());// 添加队列
//        simpleMessageListenerContainer.addQueues(queueConfig.secondQueue());// 添加队列
//        simpleMessageListenerContainer.setExposeListenerChannel(true);
//        simpleMessageListenerContainer.setMaxConcurrentConsumers(100);// 设置最大的并发的消费者数量
//        simpleMessageListenerContainer.setConcurrentConsumers(1);// 最小的并发消费者的数量
//        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
//        simpleMessageListenerContainer.setMessageListener(new MessageListener() {
//            @Override
//            public void onMessage(Message message) {
//                logger.info("====接收到[{}]队列的消息=====",message.getMessageProperties().getConsumerQueue());
//                logger.info(new String(message.getBody()));
//            }
//        });
//        return simpleMessageListenerContainer;
//    }

//    /**
//     * springboot集成的rabbitmq使用起来很方便，配置启用rabbitmq事务
//     * @param connectionFactory
//     * @return
//     */
//    @Bean("rabbitTransactionManager")
//    public RabbitTransactionManager rabbitTransactionManager(CachingConnectionFactory connectionFactory) {
//        return new RabbitTransactionManager(connectionFactory);
//    }

//    /**
//     * 开始事务，就必须关闭confirm回调
//     */
//    @PostConstruct
//    private void init() {
//        rabbitTemplate.setChannelTransacted(true);
//        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
//    }
//
//    /**
//     * 消息发送，用注解声明事务异常回滚
//     * @param msg
//     */
//    @Transactional(rollbackFor = Exception.class,transactionManager = "rabbitTransactionManager")
//    public void sendIngateQueue(TradePayModelRes msg) {
//        logger.info("消息发送 {}",msg.getOutTradeNo());
//        rabbitTemplate.convertAndSend(exchange,ingateQueue,msg);
//    }

    /**
     * 定义rabbit template用于数据的接收和发送
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        /**若使用confirm-callback或return-callback，
         * 必须要配置publisherConfirms或publisherReturns为true
         * 每个rabbitTemplate只能有一个confirm-callback和return-callback
         */
        template.setConfirmCallback(msgSendConfirmCallBack());
        template.setReturnCallback(msgSendReturnCallback());
        /**
         * 使用return-callback时必须设置mandatory为true，或者在配置中设置mandatory-expression的值为true，
         * 可针对每次请求的消息去确定’mandatory’的boolean值，
         * 只能在提供’return -callback’时使用，与mandatory互斥
         */
        template.setMandatory(true);
        return template;
    }

    /**
     * 关于 msgSendConfirmCallBack 和 msgSendReturnCallback 的回调说明：
     * 1.如果消息没有到exchange,则confirm回调,ack=false
     * 2.如果消息到达exchange,则confirm回调,ack=true
     * 3.exchange到queue成功,则不回调return
     * 4.exchange到queue失败,则回调return(需设置mandatory=true,否则不回调,消息就丢了)
     */

    /**
     * 消息确认机制
     * Confirms给客户端一种轻量级的方式，能够跟踪哪些消息被broker处理，
     * 哪些可能因为broker宕掉或者网络失败的情况而重新发布。
     * 确认并且保证消息被送达，提供了两种方式：发布确认和事务。(两者不可同时使用)
     * 在channel为事务时，不可引入确认模式；同样channel为确认模式下，不可使用事务。
     * @return
     */
    @Bean
    public MsgSendConfirmCallBack msgSendConfirmCallBack(){
        return new MsgSendConfirmCallBack();
    }

    @Bean
    public MsgSendReturnCallBack msgSendReturnCallback(){
        return new MsgSendReturnCallBack();
    }

}
