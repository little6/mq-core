package com.yql.framework.mq.listener;

import com.yql.framework.mq.model.MqMessage;

/**
 * @author wangxiaohong
 */
public interface MessageListener {

    String onMessage(MqMessage message);

    String getTopic();

    String getTag();
}
