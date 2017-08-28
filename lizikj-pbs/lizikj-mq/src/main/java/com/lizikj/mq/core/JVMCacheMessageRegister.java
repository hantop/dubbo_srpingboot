package com.lizikj.mq.core;


import org.apache.rocketmq.common.message.MessageExt;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Michael.Huang on 2017/4/1.
 */
public class JVMCacheMessageRegister implements MessageRegister {

    private Set<String> cache = new HashSet<>();

    @Override
    public boolean hasConsumed(MessageExt msg) {

        return cache.contains(genKey(msg));
    }

    @Override
    public void markConsumed(MessageExt msg) {
        cache.add(genKey(msg));

    }

    public String genKey(MessageExt msg) {
        return msg.getTopic() + ":" + msg.getTags() + ":" + msg.getKeys();
    }


}
