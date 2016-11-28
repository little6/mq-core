package com.yql.framework.mq.model;

/**
 * @author wangxiaohong
 */
public interface MqMessage {

    static final String SUCCESS = "SUCCESS";

    static final String FAIL = "FAIL";

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
