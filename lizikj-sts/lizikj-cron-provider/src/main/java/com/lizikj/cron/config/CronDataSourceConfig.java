package com.lizikj.cron.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
@Configuration
@MapperScan(basePackages = CronDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "cronSqlSessionFactory")
public class CronDataSourceConfig {
    static final String PACKAGE = "com.lizikj.cron.dao.mapper.cron";

    @Primary
    @Bean(name = "cronDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource cronDataSource() {
        return new DruidDataSource();
    }

    @Primary
    @Bean(name = "cronTransactionManager")
    public DataSourceTransactionManager cronTransactionManager(@Qualifier("cronDataSource") DataSource cronDataSource) {
        return new DataSourceTransactionManager(cronDataSource);
    }

    @Primary
    @Bean(name = "cronSqlSessionFactory")
    public SqlSessionFactory cronSqlSessionFactory(@Qualifier("cronDataSource") DataSource cronDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(cronDataSource);

        //mybatis分页
        PageHelper pageHelper = new PageHelper();
        Properties props = new Properties();
        props.setProperty("dialect", "mysql");
        props.setProperty("reasonable", "true");
        props.setProperty("supportMethodsArguments", "true");
        props.setProperty("returnPageInfo", "check");
        props.setProperty("params", "count=countSql");
        pageHelper.setProperties(props); //添加插件
        sessionFactory.setPlugins(new Interceptor[]{pageHelper});

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath*:/mybatis/cron/*.xml"));
        return sessionFactory.getObject();
    }
}
