package com.lizikj.cron.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import java.io.IOException;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.lizikj.cron.admin")
@ImportResource({"classpath:xml/dubbo-consumer.xml"})
public class Bootstrap {
    private static Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Bootstrap.class, args);

        logger.info("lizikj-cron-admin项目启动成功");
    }

}
