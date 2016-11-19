package com.yql.framework.mq;

/**
 * @author wangxiaohong
 */
public interface MessageConsumer {
    /**
     * 启动服务
     */
    public void start();


    /**
     * 关闭服务
     */
    public void shutdown();


    /**
     * 取消某个topic订阅
     *
     * @param topic
     */
    public void unsubscribe(final String topic);
}
