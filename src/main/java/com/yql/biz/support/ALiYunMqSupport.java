package com.yql.biz.support;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.yql.biz.conf.ALiYunProperties;
import com.yql.biz.exception.MqCoreException;
import com.yql.biz.producer.YqlProducer;

import java.util.Properties;

/**
 * Created by wangdayin
 */
public class ALiYunMqSupport {
    /**
     * 阿里云发送消息
     *
     * @param yqlProducer
     */
    public static void sendMessage(YqlProducer yqlProducer) throws MqCoreException {
        ALiYunProperties aLiYunProperties = new ALiYunProperties();
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.AccessKey, aLiYunProperties.getSecretId());// AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, aLiYunProperties.getSecretKey());// SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.ProducerId, yqlProducer.getProducerId());//您在控制台创建的Producer ID
        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, String.valueOf(yqlProducer.getTimeout() == null ? aLiYunProperties.getTimeout() : yqlProducer.getTimeout()));//设置发送超时时间，单位毫秒
        properties.put(PropertyKeyConst.ONSAddr, aLiYunProperties.getONSAddr());
        Producer producer = ONSFactory.createProducer(properties);
        // 在发送消息前，必须调用start方法来启动Producer，只需调用一次即可。
        producer.start();
        Message msg = new Message(yqlProducer.getTopic(), yqlProducer.getMessageTag(), yqlProducer.getMessage().getBytes());
        msg.setKey(yqlProducer.getMessageKey());
        producer.sendOneway(msg);
        producer.shutdown();
    }
}
