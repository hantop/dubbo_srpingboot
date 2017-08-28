package com.lizikj.mq.demo;

import com.lizikj.mq.constants.Tags;
import com.lizikj.mq.constants.Topic;
import com.lizikj.mq.core.Consumer;
import org.springframework.stereotype.Component;

/**
 * Created by Michael.Huang on 2017/4/1.
 */
@Component
public class SimpleConsumer extends Consumer {

    @Override
    public void consume(String message) {
        System.out.println("SimpleConsumer:" + message);

    }

    @Override
    public String followTags() {

        return Tags.HELLO.toString();
    }

    @Override
    public String followTopic() {

        return Topic.DEMO.toString();
    }

}
