package com.wangjc.rabbitmq.aspect;

import com.wangjc.rabbitmq.annotation.OperateLogRead;
import com.wangjc.rabbitmq.annotation.OperateLogWrite;
import com.wangjc.rabbitmq.core.MsgSendCore;
import com.wangjc.rabbitmq.entity.TestLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 操作日志切面,按实用场景应该用于消息队列异步记录
 * @author wangjc
 * @title: OperateLogAspect
 * @projectName wangjc-rabbitmq
 * @description: TODO
 */
@Aspect
@Component
public class OperateLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(OperateLogAspect.class);

    @Autowired
    private MsgSendCore msgSendCore;

    /**
     * 此处使用注解扫描
     * 也可以通过切点表达式直接指定需要拦截的package,需要拦截的class 以及 method
     * 切点表达式:   execution(...)
     */
    @Pointcut("@annotation(com.wangjc.rabbitmq.annotation.OperateLogRead)")
    public void logPointCutRead(){ }

    @Pointcut("@annotation(com.wangjc.rabbitmq.annotation.OperateLogWrite)")
    public void logPointCutWrite(){ }

    /**
     * 后置通知。也可以使用 @Before (前置通知)  @Around (环绕通知 ProceedingJoinPoint)
     * 实际的场景可以添加的更多的日志参数，我用了redis设置菜单项，模块信息，当前用户…………
     * @param point
     * @param operateLogRead
     */
    @After(value = "logPointCutRead() && @annotation(operateLogRead)")
    public void afterRead(JoinPoint point, OperateLogRead operateLogRead){
        TestLog log = new TestLog(){{
            setCreateTime(System.currentTimeMillis()/1000L);
            setOperate(operateLogRead.operate());
        }};
        msgSendCore.sendByFirst(log);
    }

    @AfterReturning(value = "logPointCutWrite() && @annotation(operateLogWrite)")
    public void afterWrite(JoinPoint point, OperateLogWrite operateLogWrite){
        TestLog log = new TestLog(){{
            setCreateTime(System.currentTimeMillis()/1000L);
            setOperate(operateLogWrite.operate());
        }};
        msgSendCore.sendBySecond(log);
    }

}
