package com.lizikj.mq.core;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Michael.Huang on 2017/4/1.
 * 分发处理消息监听器
 */
@Component
public class DispatchMessageListener implements MessageListenerConcurrently {

    private Logger log = LoggerFactory.getLogger(getClass());
    private Map<String, Consumer> consumers = new HashMap<>();


    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        ConsumeConcurrentlyStatus reconsumeLater = ConsumeConcurrentlyStatus.RECONSUME_LATER;
        if (msgs == null || msgs.size() == 0) {
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        MessageExt msg = msgs.get(0);
        log.info("receive message: topic={} tags={} keys={}",
                new Object[]{msg.getTopic(), msg.getTags(), msg.getKeys()});
        String key = genKey(msg.getTopic(), msg.getTags());
        Consumer consumer = consumers.get(key);
        if (consumer == null) {
            log.info("fail to found consumer. topic={} tags={}", msg.getTopic(), msg.getTags());
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        try {
            consumer.consume(msg);
            log.info("msg({}) consume sucessfully!", msg.getKeys());
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        } catch (Exception e) {
            log.warn("消息处理异常。", e);
            log.warn("失败消息信息:topic={} tags={} keys={} ",
                    new Object[]{msg.getTopic(), msg.getTags(), msg.getKeys()});
            return reconsumeLater;
        }

    }


    public void register(Consumer consumer) {
        String key = genKey(consumer.followTopic().toString(), consumer.followTags().toString());
        consumers.put(key, consumer);
        log.info("注册成功! consumer={} topic={} tags={}",
                new Object[]{consumer.getClass().getSimpleName(), consumer.followTopic(), consumer.followTags()});
    }

    public String genKey(String topic, String tags) {
        return topic + "_" + tags;
    }
}
