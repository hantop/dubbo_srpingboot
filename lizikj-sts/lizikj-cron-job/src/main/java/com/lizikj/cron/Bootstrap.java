package com.lizikj.cron;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import com.lizikj.common.util.SpringContextUtil;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.lizikj.cron")
@ImportResource({"classpath:xml/dubbo-consumer.xml"})
public class Bootstrap {
    private static Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    @Bean
    public SpringContextUtil springContextUtil() {
        return new SpringContextUtil();
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        new SpringApplicationBuilder().sources(Bootstrap.class).web(false).run(args);

        logger.info("lizikj-cron-job项目启动成功");
    }

}
