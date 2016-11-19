package com.yql.framework.mq;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.bean.Subscription;

import java.util.Map;
import java.util.Set;

/**
 * @author wangxiaohong
 */
public class AliYunMessageConsumer implements MessageConsumer {

    private Consumer consumer;

    private Map<Subscription, MessageListener> subscriptionTable;

    public AliYunMessageConsumer(Consumer consumer, Map<Subscription, MessageListener> subscriptionTable) {
        this.consumer = consumer;
        this.subscriptionTable = subscriptionTable;
    }

    @Override
    public void start() {
        Set<Map.Entry<Subscription, MessageListener>> entrySet = subscriptionTable.entrySet();
        for (Map.Entry<Subscription, MessageListener> next : entrySet) {
            this.consumer.subscribe(next.getKey().getTopic(), next.getKey().getExpression(), next.getValue());
        }
        this.consumer.start();
    }

    @Override
    public void shutdown() {
        this.consumer.shutdown();
    }

    @Override
    public void unsubscribe(String topic) {
        this.consumer.unsubscribe(topic);
    }
}
