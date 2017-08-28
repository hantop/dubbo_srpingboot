package com.lizikj.mq.producer;

import com.lizikj.mq.exception.MQException;
import org.apache.rocketmq.client.producer.SendResult;

import java.io.Serializable;

/**
 * Created by Michael.Huang on 2017/4/1.
 */
public interface MessagePublisher {

    SendResult publish(String topic, String tags, String content) throws MQException;

    SendResult publish(String topic, String tags, byte[] content) throws MQException;

    SendResult publish(String topic, String tags, Serializable object) throws MQException;

}
