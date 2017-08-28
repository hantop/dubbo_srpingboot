package com.lizikj.cron.config;

import javax.sql.DataSource;

import com.lizikj.cron.listener.SimpleJobListener;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
@Configuration
public class SchedulerConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return new com.alibaba.druid.pool.DruidDataSource();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(SpringBeanJobFactory springBeanJobFactory, DataSource dataSource) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setConfigLocation(new ClassPathResource("config/quartz.properties"));

        schedulerFactoryBean.setOverwriteExistingJobs(true);
        schedulerFactoryBean.setJobFactory(springBeanJobFactory);

        schedulerFactoryBean.setDataSource(dataSource);

//        schedulerFactoryBean.setSchedulerListeners(new SimpleSchedulerListener());
        schedulerFactoryBean.setGlobalJobListeners(new SimpleJobListener());
//        schedulerFactoryBean.setGlobalTriggerListeners(new SimpleTriggerListener());
        return schedulerFactoryBean;
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        return new AutoWiringSpringBeanJobFactory();
    }
}
