package com.yql.framework.mq.model;

import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @author wangxiaohong
 */
public class TextMessage implements MqMessage, Serializable {
    private String topic;
    private String tag;
    private String key;
    private byte[] body;
    private long startDeliverTime;
    private int reconsumeTimes;
    private String msgID;

    public TextMessage(String topic, String tag, String body) {
        this.topic = topic;
        this.tag = tag;
        this.body = body.getBytes();
    }

    public TextMessage(String tag, String topic, String body, String key) {
        this.tag = tag;
        this.topic = topic;
        this.body = body.getBytes();
        this.key = key;
    }

    public TextMessage(String tag, String topic, byte[] body, String key, String msgID) {
        this.tag = tag;
        this.topic = topic;
        this.body = body;
        this.key = key;
        this.msgID = msgID;
    }

    public TextMessage(String topic, String tag, Object body) {
        this.topic = topic;
        this.tag = tag;
        this.body = JSON.toJSONBytes(body);
    }

    public TextMessage(String tag, String topic, Object body, String key) {
        this.tag = tag;
        this.topic = topic;
        this.body = JSON.toJSONBytes(body);
        this.key = key;
    }

    @Override
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public byte[] getBodyAsBytes() {
        return this.body;
    }

    @Override
    public String getBodyAsText() {
        return new String(this.body);
    }

    @Override
    public <T> T getBodyAs(Class<T> bodyClass) {
        return JSON.parseObject(this.body, bodyClass);
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public void setReconsumeTimes(final int reconsumeTimes) {
        this.reconsumeTimes = reconsumeTimes;
    }

    @Override
    public int getReconsumeTimes() {
        return reconsumeTimes;
    }

    @Override
    public long getStartDeliverTime() {
        return this.startDeliverTime;
    }

    /**
     * 设置消息的定时投递时间（绝对时间),最大延迟时间为7天.
     * <p>例1: 延迟投递, 延迟3s投递, 设置为: System.currentTimeMillis() + 3000;
     * <p>例2: 定时投递, 2016-02-01 11:30:00投递, 设置为: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-02-01 11:30:00").getTime()
     */
    public void setStartDeliverTime(final long startDeliverTime) {
        this.startDeliverTime = startDeliverTime;
    }

    @Override
    public String getMsgID() {
        return msgID;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }
}
