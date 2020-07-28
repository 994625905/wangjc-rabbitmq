package com.wangjc.rabbitmq.constant;

/**
 * RabbitMq需要的常量
 * @author wangjc
 * @title: RabbitMQConstant
 * @projectName wangjc-rabbitmq
 * @description: TODO
 */
public class RabbitMQConstant {

    /** 消息交换机的名字 */
    public interface EXCHANGE{
        String DIRECT_ONE = "wangjc_rabbitmq_exchange_direct_one";
    }

    /** 队列的名称 */
    public interface QUEUE_NAME{
        String FIRST = "firstQueue";
        String SECOND = "secondQueue";
    }

    /** 队列的路由key */
    public interface ROUTING{
        String FIRST = "wangjc_rabbitmq_queue_one_first";
        String SECOND = "wangjc_rabbitmq_queue_one_second";
    }

}
