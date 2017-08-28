package com.lizikj.mq.core;


import org.apache.rocketmq.common.message.MessageExt;

/**
 * Created by Michael.Huang on 2017/4/1.
 */
public interface MessageRegister {

    boolean hasConsumed(MessageExt msg);

    void markConsumed(MessageExt msg);

}
