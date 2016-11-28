package com.yql.framework.mq;

import com.aliyun.openservices.ons.api.PropertyKeyConst;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

/**
 * @author  wangdayin
 * @author wangxiaohong
 */
@ConfigurationProperties(prefix = "yql.mq")
public class AliYunProperties extends Properties {

    public Object getAccessKey() {
        return get(PropertyKeyConst.AccessKey);
    }

    public Object getSecretKey() {
        return get(PropertyKeyConst.SecretKey);
    }

    public Object getONSAddr() {
        return get(PropertyKeyConst.ONSAddr);
    }
}
