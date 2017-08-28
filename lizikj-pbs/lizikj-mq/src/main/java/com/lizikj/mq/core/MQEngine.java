package com.lizikj.mq.core;

import org.apache.rocketmq.client.consumer.MQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.MQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Michael.Huang on 2017/4/1.
 */
@Component
public class MQEngine {

    protected Logger log = LoggerFactory.getLogger(getClass());
    @Autowired(required = false)
    private MQProducer mqProducer;

    @Autowired(required = false)
    private MQPushConsumer consumer;

    @PostConstruct
    public void start() throws MQClientException {
        if (consumer != null) {
            consumer.start();
            log.info("consumer start successfully!");
        }
        if (mqProducer != null) {
            mqProducer.start();
            log.info("producer start successfully!");
        }

    }


}
