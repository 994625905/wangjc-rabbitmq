package com.wangjc.rabbitmq.annotation;

import java.lang.annotation.*;

/**
 * 声明写操作
 * @author wangjc
 * @title: OperateLogWrite
 * @projectName wangjc-rabbitmq
 * @description: TODO
 */
@Target({ElementType.METHOD}) // 只作用于方法
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperateLogWrite {

    /**
     * 描述信息
     * @return
     */
    String operate() default "";

}
