package com.yql.mqserver.consumer;

import com.aliyun.openservices.ons.api.*;
import com.yql.mqserver.conf.ALiYunProperties;
import com.yql.mqserver.web.MQHanding;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

/**
 * Created by wangdayin
 * 消息订阅者
 */
@ConfigurationProperties(prefix = "yql.mq")
public class YqlConsumer extends ALiYunProperties {
    //主题
    private String topic;
    //订阅者id
    private String consumerId;
    //订阅消息的messageTag，多个tag以||分割
    private String messageTag = "*";

    public YqlConsumer() {
    }


    public YqlConsumer(String topic, String consumerId) {
        this.topic = topic;
        this.consumerId = consumerId;
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

    /**
     * 订阅消息
     */
    public void subscribeMessage(MQHanding handing) throws RuntimeException {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, getConsumerId());
        properties.put(PropertyKeyConst.AccessKey, super.getSecretId());
        properties.put(PropertyKeyConst.SecretKey, super.getSecretKey());
        properties.put(PropertyKeyConst.ONSAddr, super.getONSAddr());
        Consumer consumer = ONSFactory.createConsumer(properties);
        //参数：topicId,tag(监听多个tag用||分割)
        consumer.subscribe(getTopic(), getMessageTag(), new MessageListener() {
            public Action consume(Message message, ConsumeContext context) {
                handing.messageHanding(message);
                return Action.CommitMessage;
            }
        });
        consumer.start();
    }
}
