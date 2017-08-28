package com.lizikj.mq.core;

import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;

/**
 * Created by Michael.Huang on 2017/4/1.
 */
public abstract class Consumer {
    protected Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private DispatchMessageListener consumerRegister;

    public abstract String followTags();

    public abstract String followTopic();
    /**
     * 不能重复消费的子类可重写该方法
     * @return
     */
    /**
     * 不再支持，消费端保持冥等
     */
//	public boolean recallable(){
//		return true;
//	};
    @PostConstruct
    public void register() {
        consumerRegister.register(this);
    }

    /**
     * 要获取完整message可在子类重写该方法
     *
     * @param message
     */
    public void consume(MessageExt message) {
        String content = new String(message.getBody(), Charset.defaultCharset());
        log.info("content={}", content);
        this.consume(content);
    }

    public abstract void consume(String content);


}
