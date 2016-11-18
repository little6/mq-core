package com.yql.mqserver.producer;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.yql.mqserver.conf.ALiYunProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

/**
 * Created by wangdayin
 * 消息生产者
 */
@ConfigurationProperties(prefix = "yql.mq")
public class YqlProducer extends ALiYunProperties {
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

    public YqlProducer(String message) {
        this.message = message;
    }

    public YqlProducer(String messageTag, String messageKey, String message) {
        this.messageTag = messageTag;
        this.messageKey = messageKey;
        this.message = message;
    }

    public YqlProducer(String messageKey, String message) {
        this.messageKey = messageKey;
        this.message = message;
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

    /**
     * 阿里云发送消息
     */
    public void sendMessage() throws RuntimeException {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.AccessKey, getSecretId());// AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, getSecretKey());// SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.ProducerId, getProducerId());//您在控制台创建的Producer ID
        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, String.valueOf(getTimeout() == null ? super.getTimeout() : getTimeout()));//设置发送超时时间，单位毫秒
        properties.put(PropertyKeyConst.ONSAddr, getONSAddr());
        Producer producer = ONSFactory.createProducer(properties);
        // 在发送消息前，必须调用start方法来启动Producer，只需调用一次即可。
        producer.start();
        Message msg = new Message(getTopic(), getMessageTag(), getMessage().getBytes());
        msg.setKey(getMessageKey());
        producer.sendOneway(msg);
        producer.shutdown();
    }

}
