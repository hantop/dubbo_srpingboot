package com.lizikj.mq.demo;

import com.alibaba.rocketmq.client.producer.MQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.lizikj.mq.constants.Tags;
import com.lizikj.mq.constants.Topic;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.Charset;

/**
 * Created by Michael.Huang on 2017/4/1.
 */
public class MQTest extends BaseTest {
    @Autowired
    MQProducer producer;


    @Test
    public void simple() throws Exception {
        String msg = "hello rotket mq!";
        SendResult result = producer.send(new Message("TAS", Tags.HELLO.toString(), msg.getBytes(Charset.defaultCharset())));
        System.out.println(result.getSendStatus());
//		waitConsume(20);
    }

    @Test
    public void ext() throws Exception {
        String msg = "rich message";
        SendResult result = producer.send(new Message(Topic.DEMO.toString(), Tags.EXT.toString(), msg.getBytes(Charset.defaultCharset())));
        System.out.println(result.getSendStatus());
        waitConsume(20);
    }

    //不再支持
    @Test
    public void notRecallable() throws Exception {
//		String msg = "100";		
        for (int i = 1; i <= 10; i++) {
            String keys = "simplekeyabc" + i;
//			for (int j = 0; j < 3; j++) {
            SendResult result = producer.send(new Message(Topic.DEMO.toString(), "TMD", ("msg" + i).getBytes(Charset.defaultCharset())));
            System.out.println(result.getSendStatus());
//			}	
        }
//		waitConsume(30);
    }

    private void waitConsume(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }

    }


}
