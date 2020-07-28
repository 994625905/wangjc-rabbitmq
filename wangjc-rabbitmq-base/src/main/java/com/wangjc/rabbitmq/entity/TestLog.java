package com.wangjc.rabbitmq.entity;

import java.io.Serializable;

/**
 * @author wangjc
 * @title: TestLog
 * @projectName wangjc-rabbitmq
 * @description: TODO
 */
public class TestLog implements Serializable {

    private static final long serialVersionUID = 8700610537969738042L;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 操作说明
     */
    private String operate;

    /** 其他字段………… */

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }
}
