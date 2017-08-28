package com.lizikj.mq.producer;

import com.alibaba.fastjson.JSON;
import com.lizikj.common.util.DateUtils;
import com.lizikj.mq.exception.MQException;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Random;

/**
 * Created by Michael.Huang on 2017/4/1.
 */
@Service
public class RocketMQMessagePublisher implements MessagePublisher {
    protected Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private MQProducer producer;

    @Override
    public SendResult publish(String topic, String tags, Serializable object) throws MQException {
        //转为json，监控端可读
        return publish(topic, tags, JSON.toJSONString(object));
    }

    @Override
    public SendResult publish(String topic, String tags, String content) throws MQException {
        return publish(topic, tags, content.getBytes(Charset.defaultCharset()));

    }

    @Override
    public SendResult publish(String topic, String tags, byte[] content) throws MQException {
        String keys = genKeys();
        Message message = new Message(topic.toString(), tags.toString(), keys, content);
        log.info("message publishing... topic={} tags={} keys={}",
                new Object[]{topic, tags, keys});
        try {
            SendResult sendResult = producer.send(message);
            log.info("message published. keys={} sendResult={}", keys, sendResult);
            return sendResult;
        } catch (Exception e) {
            log.error("发布异常. tags={} keys={}", tags, keys);
            throw new MQException("发布异常", e);
        }
    }

    private String genKeys() {

        return "MSG" + DateUtils.getCurrent() + (1000 + new Random().nextInt(9000));
    }


}
