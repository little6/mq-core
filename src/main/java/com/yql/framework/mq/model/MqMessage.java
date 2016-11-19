package com.yql.framework.mq.model;

/**
 * @author wangxiaohong
 */
public interface MqMessage {
    String getTopic();

    String getTag();

    String getKey();

    byte[] getBody();

    String getMsgID();

    int getReconsumeTimes();

    long getStartDeliverTime();
}
