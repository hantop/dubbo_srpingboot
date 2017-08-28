package com.lizikj.cron;

import com.lizikj.common.util.SpringContextUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@ComponentScan(basePackages = "com.lizikj.cron,com.lizikj.mq")
@MapperScan({"com.lizikj.cron.dao.mapper"})
@ImportResource("classpath:xml/dubbo-service.xml")
public class Bootstrap {
    private static Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    public static void main(String[] args) throws InterruptedException, IOException {
        ApplicationContext ctx = new SpringApplicationBuilder().sources(Bootstrap.class).web(false).run(args);

        logger.info("lizikj-cron-provider项目启动成功");
        CountDownLatch closeLatch = ctx.getBean(CountDownLatch.class);

        closeLatch.await();
    }

    @Bean
    public SpringContextUtil springContextUtil() {
        return new SpringContextUtil();
    }

    @Bean
    public CountDownLatch closeLatch() {
        return new CountDownLatch(1);
    }

}
