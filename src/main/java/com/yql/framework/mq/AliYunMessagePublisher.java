package com.yql.framework.mq;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.yql.framework.mq.model.MqMessage;
import com.yql.framework.mq.model.Result;

/**
 * @author wangxiaohong
 */
public class AliYunMessagePublisher implements MessagePublisher {
    private Producer producer;

    public AliYunMessagePublisher(Producer producer) {
        this.producer = producer;
    }

    @Override
    public Result send(MqMessage message) {
        Message m = new Message(message.getTopic(), message.getTag(), message.getKey(), message.getBody());
        SendResult sendResult = producer.send(m);
        Result result = new Result();
        result.setMessageId(sendResult.getMessageId());
        result.setTopic(sendResult.getTopic());
        return result;
    }

    @Override
    public void start() {
        this.producer.start();
    }

    @Override
    public void shutdown() {
        this.producer.shutdown();
    }
}
