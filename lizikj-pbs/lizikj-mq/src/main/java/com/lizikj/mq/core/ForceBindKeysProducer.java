package com.lizikj.mq.core;


import com.lizikj.common.util.DateUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionExecuter;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.Random;

/**
 * Created by Michael.Huang on 2017/4/1.
 */
public class ForceBindKeysProducer extends TransactionMQProducer {

    @Override
    public TransactionSendResult sendMessageInTransaction(Message msg,
                                                          LocalTransactionExecuter tranExecuter, Object arg)
            throws MQClientException {
        addKeysWhenNotExist(msg);
        return super.sendMessageInTransaction(msg, tranExecuter, arg);
    }


    @Override
    public void sendOneway(Message msg) throws MQClientException,
            RemotingException, InterruptedException {
        addKeysWhenNotExist(msg);
        super.sendOneway(msg);
    }


    @Override
    public SendResult send(Message msg) throws MQClientException,
            RemotingException, MQBrokerException, InterruptedException {
        addKeysWhenNotExist(msg);
        return super.send(msg);
    }


    @Override
    public SendResult send(Message msg, long timeout) throws MQClientException,
            RemotingException, MQBrokerException, InterruptedException {
        addKeysWhenNotExist(msg);
        return super.send(msg, timeout);
    }

    private void addKeysWhenNotExist(Message msg) {
        if (msg.getKeys() == null) {
            msg.setKeys(genKeys());
        }

    }

    private String genKeys() {

        return "MSG" + DateUtils.getCurrent() +(1000 + new Random().nextInt(9000));
    }


}
