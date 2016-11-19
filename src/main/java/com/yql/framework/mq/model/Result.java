package com.yql.framework.mq.model;

/**
 * @author wangxiaohong
 */
public class Result {
    private String messageId;
    private String topic;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
