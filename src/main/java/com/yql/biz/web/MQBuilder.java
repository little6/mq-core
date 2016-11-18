package com.yql.biz.web;

import com.yql.biz.exception.MqCoreException;
import com.yql.biz.producer.YqlProducer;
import com.yql.biz.support.ALiYunMqSupport;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import java.io.Serializable;

/**
 * Created by wangdayin
 */
@Configurable(autowire = Autowire.BY_TYPE, preConstruction = true)
public class MQBuilder implements Serializable {

    /**
     * 发送消息
     *
     * @param yqlProducer
     */
    public static void sendMessage(YqlProducer yqlProducer) throws MqCoreException {
        ALiYunMqSupport.sendMessage(yqlProducer);
    }

    public static void subscribeMessage() {

    }
}
