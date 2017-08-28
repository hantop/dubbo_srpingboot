package com.lizikj.mq.demo;

import com.lizikj.mq.constants.Tags;
import com.lizikj.mq.producer.MessagePublisher;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Michael.Huang on 2017/4/1.
 */
public class PublisherTest extends BaseTest {
    @Autowired
    MessagePublisher publisher;


    @Test
    public void simple() throws Exception {
        String msg = "hello rotket mq!";
        publisher.publish("POINT", Tags.HELLO.toString(), msg);
    }


}
