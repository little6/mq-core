package com.yql.biz.exception;

import org.springframework.context.support.MessageSourceAccessor;

import javax.annotation.Resource;

/**
 * Created by wangdayin
 * 消息中心自定义异常
 */
public class MqCoreException extends RuntimeException {
    private String messageKey;
    @Resource
    transient private MessageSourceAccessor messageSourceAccessor;

    public MqCoreException(String messageKey) {
        this.messageKey = messageKey;
    }


    @Override
    public String getMessage() {
        return messageSourceAccessor.getMessage("error.send.message");
    }
}
