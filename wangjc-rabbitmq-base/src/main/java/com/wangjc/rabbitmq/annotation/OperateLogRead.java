package com.wangjc.rabbitmq.annotation;

import java.lang.annotation.*;

/**
 * 声明读操作
 * @author wangjc
 * @title: OperateLogRead
 * @projectName wangjc-rabbitmq
 * @description: TODO
 */
@Target({ElementType.METHOD}) // 只作用于方法
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperateLogRead {

    /**
     * 描述信息
     * @return
     */
    String operate() default "";

}
