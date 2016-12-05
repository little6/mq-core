package com.yql.framework.mq;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.bean.Subscription;
import com.yql.framework.mq.model.MqMessage;
import com.yql.framework.mq.model.TextMessage;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

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
        Map<String, List<com.yql.framework.mq.listener.MessageListener>> topicMap = new HashMap<>();
        for (com.yql.framework.mq.listener.MessageListener listener : listeners) {
            topicMap.compute(listener.getTopic(), (s, messageListeners) -> {
                List<com.yql.framework.mq.listener.MessageListener> list = new ArrayList<>();
                if (!CollectionUtils.isEmpty(messageListeners)) {
                    list.addAll(messageListeners);
                }
                list.add(listener);
                return list;
            });
        }

        topicMap.forEach((topic, messageListeners) -> {
            Subscription subscription = new Subscription();
            subscription.setTopic(topic);
            subscription.setExpression("*");
            map.put(subscription, (message, context) -> {
                MqMessage m = new TextMessage(message.getTopic(), message.getTag(), message.getKey(), message.getBody(), message.getMsgID());
                for (com.yql.framework.mq.listener.MessageListener messageListener : messageListeners) {
                    String tag = messageListener.getTag();
                    String[] tags = StringUtils.delimitedListToStringArray(tag,"||");
                    Iterator<String> iterator = Arrays.asList(tags).iterator();
                    if (CollectionUtils.contains(iterator, m.getTag())) {
                        String result = messageListener.onMessage(m);
                        if (!MqMessage.SUCCESS.equals(result)) {
                            return Action.ReconsumeLater;
                        }
                    }
                }
                return Action.CommitMessage;
            });
        });
        return map;
    }
}
