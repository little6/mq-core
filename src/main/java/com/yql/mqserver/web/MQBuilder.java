package com.yql.mqserver.web;

import com.yql.mqserver.consumer.YqlConsumer;
import com.yql.mqserver.producer.YqlProducer;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * Created by wangdayin
 */
@Configurable(autowire = Autowire.BY_TYPE, preConstruction = true)
public class MQBuilder {

    /**
     * 发送消息
     *
     * @param yqlProducer
     */
    public void sendMessage(YqlProducer yqlProducer) {
        yqlProducer.sendMessage();
    }

    /**
     * 订阅消息
     *
     * @param yqlConsumer
     * @param mqHanding
     */
    public void subscribeMessage(YqlConsumer yqlConsumer, MQHanding mqHanding) {
        yqlConsumer.subscribeMessage(mqHanding);
    }
}
