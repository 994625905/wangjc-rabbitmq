package com.wangjc.rabbitmq.web;

import com.wangjc.rabbitmq.annotation.OperateLogRead;
import com.wangjc.rabbitmq.annotation.OperateLogWrite;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wangjc
 * @title: TestController
 * @projectName wangjc-rabbitmq
 * @description: TODO
 */
@Controller
@RequestMapping(value = "/test")
public class TestController {

    /**
     * 测试：读
     */
    @RequestMapping(value = "/read",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    @OperateLogRead(operate = "测试读操作")
    public String read(){
        return "测试读操作，切面植入日志";
    }

    /**
     * 测试：写
     * @return
     */
    @RequestMapping(value = "/write",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    @OperateLogWrite(operate = "测试写操作")
    public String write(){
        return "测试写操作，切面植入日志";
    }

}
