package com.yql.biz.conf;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.Locale;

/**
 * Created by wangdayin
 */
@Configuration
public class WebConfig {
    @Bean
    @ConditionalOnMissingBean
    public MessageSourceAccessor sourceAccessor(MessageSource messageSource) {
        return new MessageSourceAccessor(messageSource, Locale.CHINA);
    }
}
