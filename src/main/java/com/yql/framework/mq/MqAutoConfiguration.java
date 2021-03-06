package com.yql.framework.mq;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactoryAPI;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.impl.ONSFactoryImpl;
import com.aliyun.openservices.ons.api.impl.rocketmq.ConsumerImpl;
import com.aliyun.openservices.ons.api.impl.rocketmq.ProducerImpl;
import com.yql.framework.mq.listener.MessageListener;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.List;
import java.util.Properties;

/**
 * @author wangxiaohong
 */
@Configuration
@ConditionalOnProperty(prefix = "yql", name = "mq", matchIfMissing = true)
@ConditionalOnClass(value = {Producer.class, ProducerImpl.class, Consumer.class, ConsumerImpl.class})
@EnableConfigurationProperties(AliYunProperties.class)
public class MqAutoConfiguration {

    @Configuration
    @ConditionalOnClass({ONSFactoryImpl.class})
    protected static class ONSFactoryConfiguration {
        @Bean
        @DependsOn(value = "onsSystemEnvInitializer")
        public ONSFactoryAPI onsFactory() {
            return new ONSFactoryImpl();
        }

        @Bean("onsSystemEnvInitializer")
        @ConfigurationProperties(prefix = "ons.client")
        public OnsSystemEnvInitializer onsSystemEnvInitializer() {
            return new OnsSystemEnvInitializer();
        }
    }

    protected static class OnsSystemEnvInitializer extends Properties implements InitializingBean {
        @Override
        public void afterPropertiesSet() throws Exception {
            this.forEach((k, v) -> System.setProperty("ons.client." + k, String.valueOf(v)));
        }
    }

    @Configuration
    @ConditionalOnProperty(prefix = "yql.mq.producer", name = "ProducerId")
    @ConfigurationProperties(prefix = "yql.mq.producer")
    protected static class ProducerConfiguration extends Properties {

        private transient ONSFactoryAPI factoryAPI;

        public ProducerConfiguration(ONSFactoryAPI factoryAPI, AliYunProperties aliYunProperties) {
            this.factoryAPI = factoryAPI;
            put(PropertyKeyConst.AccessKey, aliYunProperties.getAccessKey());
            put(PropertyKeyConst.SecretKey, aliYunProperties.getSecretKey());
            put(PropertyKeyConst.ONSAddr, aliYunProperties.getONSAddr());
        }

        @ConditionalOnMissingBean(MessagePublisher.class)
        @Bean(initMethod = "start", destroyMethod = "shutdown")
        public MessagePublisher publisher() {
            return new AliYunMessagePublisher(factoryAPI.createProducer(this));
        }
    }

    @Configuration
    @ConditionalOnProperty(prefix = "yql.mq.consumer", name = "ConsumerId")
    @ConditionalOnBean(MessageListener.class)
    @ConfigurationProperties(prefix = "yql.mq.consumer")
    protected static class ConsumerConfiguration extends Properties {
        private transient ONSFactoryAPI factoryAPI;

        public ConsumerConfiguration(ONSFactoryAPI factoryAPI, AliYunProperties aliYunProperties) {
            this.factoryAPI = factoryAPI;
            put(PropertyKeyConst.AccessKey, aliYunProperties.getAccessKey());
            put(PropertyKeyConst.SecretKey, aliYunProperties.getSecretKey());
            put(PropertyKeyConst.ONSAddr, aliYunProperties.getONSAddr());
        }

        @ConditionalOnMissingBean(MessageConsumer.class)
        @Bean(initMethod = "start", destroyMethod = "shutdown")
        public MessageConsumer aliYunMessageConsumer(List<com.yql.framework.mq.listener.MessageListener> listeners) {
            Consumer consumer = factoryAPI.createConsumer(this);
            return new AliYunMessageConsumer(consumer, listeners);
        }
    }
}
