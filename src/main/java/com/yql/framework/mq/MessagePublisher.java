package com.yql.framework.mq;

import com.yql.framework.mq.model.MqMessage;
import com.yql.framework.mq.model.Result;

/**
 * @author wangxiaohong
 */
public interface MessagePublisher {

    void start();

    void shutdown();

    Result send(final MqMessage message);
}
