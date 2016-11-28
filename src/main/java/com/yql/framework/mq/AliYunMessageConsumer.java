package com.yql.framework.mq;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.bean.Subscription;
import com.yql.framework.mq.model.MqMessage;
import com.yql.framework.mq.model.TextMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wangxiaohong
 */
public class AliYunMessageConsumer implements MessageConsumer {

    private Consumer consumer;

    private Map<Subscription, MessageListener> subscriptionTable;

    public AliYunMessageConsumer(Consumer consumer, List<com.yql.framework.mq.listener.MessageListener> listeners) {
        this.consumer = consumer;
        this.subscriptionTable = messageListenerMap(listeners);
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

    private Map<Subscription, com.aliyun.openservices.ons.api.MessageListener> messageListenerMap(List<com.yql.framework.mq.listener.MessageListener> listeners) {
        Map<Subscription, com.aliyun.openservices.ons.api.MessageListener> map = new HashMap<>();
        for (com.yql.framework.mq.listener.MessageListener listener : listeners) {
            Subscription subscription = new Subscription();
            subscription.setTopic(listener.getTopic());
            subscription.setExpression(listener.getTag());
            map.put(subscription, (message, context) -> {
                MqMessage m = new TextMessage(message.getTopic(), message.getTag(), message.getKey(), message.getBody(), message.getMsgID());
                String result = listener.onMessage(m);
                if (MqMessage.SUCCESS.equals(result)) {
                    return Action.CommitMessage;
                }
                return Action.ReconsumeLater;
            });
        }
        return map;
    }
}
