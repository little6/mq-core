package com.yql.mqserver.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wangdayin
 */
@ConfigurationProperties(prefix = "yql.mq")
public class ALiYunProperties {
    //签名id
    private String secretId = "LTAIukPGqYkArvKd";
    //签名
    private String secretKey = "uySPaK3rIxN5EuIjF8Rsbdbvxlkvvf";
    //发送消息服务器地址
    private String ONSAddr = "http://onsaddr-internet.aliyun.com/rocketmq/nsaddr4client-internet";
    //消息发送超时时间
    private Integer timeout = 3000;

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getONSAddr() {
        return ONSAddr;
    }

    public void setONSAddr(String ONSAddr) {
        this.ONSAddr = ONSAddr;
    }
}
