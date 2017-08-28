package com.lizikj.cron.listener;

import com.lizikj.cron.service.IQuartzService;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    private final static Logger logger = LoggerFactory.getLogger(ApplicationStartup.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        IQuartzService quartzService = applicationContext.getBean(IQuartzService.class);
        try {
            quartzService.runAllJob();
        } catch (SchedulerException e) {
            logger.error("启动任务异常:", e);
        } catch (ClassNotFoundException e) {
            logger.error("启动任务异常:", e);
        }
    }
}
