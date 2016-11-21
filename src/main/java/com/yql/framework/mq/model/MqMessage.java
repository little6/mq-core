package com.yql.framework.mq.model;

/**
 * @author wangxiaohong
 */
public interface MqMessage {
    String getTopic();

    String getTag();

    String getKey();

    byte[] getBodyAsBytes();

    String getMsgID();

    String getBodyAsText();

    <T> T getBodyAs(Class<T> bodyClass);

    int getReconsumeTimes();

    long getStartDeliverTime();
}
