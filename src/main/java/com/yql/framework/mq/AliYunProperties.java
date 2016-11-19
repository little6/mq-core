package com.yql.framework.mq;

import com.aliyun.openservices.ons.api.PropertyKeyConst;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

/**
 * Created by wangdayin
 */
@ConfigurationProperties(prefix = "yql.mq")
public class AliYunProperties extends Properties {
    //签名id
    private String accessKey = "LTAIukPGqYkArvKd";
    //签名
    private String secretKey = "uySPaK3rIxN5EuIjF8Rsbdbvxlkvvf";
    //发送消息服务器地址
    private String ONSAddr = "http://onsaddr-internet.aliyun.com/rocketmq/nsaddr4client-internet";

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
        put(PropertyKeyConst.AccessKey, accessKey);
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
        put(PropertyKeyConst.SecretKey, accessKey);
    }

    public String getONSAddr() {
        return ONSAddr;
    }

    public void setONSAddr(String ONSAddr) {
        this.ONSAddr = ONSAddr;
        put(PropertyKeyConst.ONSAddr, accessKey);
    }
}
