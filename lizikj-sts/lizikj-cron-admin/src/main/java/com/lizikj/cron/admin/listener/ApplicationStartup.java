package com.lizikj.cron.admin.listener;

import com.lizikj.cron.admin.service.ISchedulerConnectorService;
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
        ISchedulerConnectorService schedulerConnectorService = applicationContext.getBean(ISchedulerConnectorService.class);
        try {
            schedulerConnectorService.initialize();
        } catch (Exception ex) {
            logger.error("initialize scheduler connector error:", ex);
        }
    }
}