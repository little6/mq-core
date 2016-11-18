package com.yql.biz.producer;

/**
 * Created by wangdayin
 * 消息生产者
 */

public class YqlProducer {
    //主题
    private String topic;
    //申请的生产者id
    private String producerId;
    //消息发送超时时间
    private Integer timeout;
    //发送的消息分组
    private String messageTag;
    //发送消息的key
    private String messageKey;
    //消息体
    private String message;


    public YqlProducer() {
    }


    public YqlProducer(String topic, String producerId, String messageTag, String messageKey, String message) {
        this.topic = topic;
        this.producerId = producerId;
        this.messageTag = messageTag;
        this.messageKey = messageKey;
        this.message = message;
    }

    public YqlProducer(String topic, String producerId, String messageTag, String messageKey, String message, Integer timeout) {
        this.topic = topic;
        this.producerId = producerId;
        this.timeout = timeout;
        this.messageTag = messageTag;
        this.messageKey = messageKey;
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getMessageTag() {
        return messageTag;
    }

    public void setMessageTag(String messageTag) {
        this.messageTag = messageTag;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "YqlProducer{" +
                "topic='" + topic + '\'' +
                ", producerId='" + producerId + '\'' +
                ", timeout=" + timeout +
                ", messageTag='" + messageTag + '\'' +
                ", messageKey='" + messageKey + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
