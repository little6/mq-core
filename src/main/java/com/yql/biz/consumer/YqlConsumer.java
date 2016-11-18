package com.yql.biz.consumer;

/**
 * Created by wangdayin
 * 消息订阅者
 */
public class YqlConsumer {
    //主题
    private String topic;
    //订阅者id
    private String consumerId;
    //订阅消息的messageTag，多个tag以||分割
    private String messageTag;

    public YqlConsumer() {
    }


    public YqlConsumer(String topic, String consumerId) {
        this.topic = topic;
        this.consumerId = consumerId;
        this.messageTag = "*";
    }

    public YqlConsumer(String topic, String consumerId, String messageTag) {
        this.topic = topic;
        this.consumerId = consumerId;
        this.messageTag = messageTag;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getMessageTag() {
        return messageTag;
    }

    public void setMessageTag(String messageTag) {
        this.messageTag = messageTag;
    }
}
