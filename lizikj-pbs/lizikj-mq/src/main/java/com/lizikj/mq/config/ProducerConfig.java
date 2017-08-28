package com.lizikj.mq.config;

import com.lizikj.mq.core.ForceBindKeysProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Michael.Huang on 2017/4/1.
 */
@Configuration
public class ProducerConfig {

    @Bean(destroyMethod = "shutdown")
    @ConfigurationProperties(prefix = "rocket")
    public TransactionMQProducer defaultMQProducer() {
        TransactionMQProducer mqProducer = new ForceBindKeysProducer();
        return mqProducer;
    }

}
