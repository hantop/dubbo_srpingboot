package com.lizikj.mq.config;

import com.lizikj.mq.core.DispatchMessageListener;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Michael.Huang on 2017/4/1.
 */
@Configuration
public class ConsumerConfig {

    protected Logger log = LoggerFactory.getLogger(getClass());
    @Value("${consumer.topics:NO_CONFIG}")
    private String topics;
    @Autowired
    private DispatchMessageListener listener;

    @Bean(destroyMethod = "shutdown")
    @ConfigurationProperties(prefix = "rocket")
    public DefaultMQPushConsumer defaultMQPushConsumer() throws MQClientException {
        if (topics.equals("NO_CONFIG")) {
            log.warn("consumer.topics没有配置. MQPushConsumer没有启动。");
            return null;
        }
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        String[] topic = topics.split(",");
        for (String atopic : topic) {
            log.info("adding topics. topic={}", atopic);
            consumer.subscribe(atopic, "*");
        }
        consumer.setMessageListener(listener);
        return consumer;
    }

}
