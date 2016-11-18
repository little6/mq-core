package com.yql.mqserver.web;

import com.aliyun.openservices.ons.api.Message;

/**
 * Created by wangdayin
 */
public interface MQHanding {
    void messageHanding(Message message);
}
