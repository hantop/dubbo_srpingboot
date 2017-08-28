package com.lizikj.datasource.readwrite.config;

import com.github.pagehelper.PageHelper;
import com.lizikj.common.enums.DataSourceTypeEnum;
import com.lizikj.common.util.SpringContextUtil;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * Created by Michael.Huang on 2017/4/1.
 * 读写分离配置
 */
@Configuration
@AutoConfigureAfter({DataSourceConfiguration.class})
@DependsOn({"writeDataSource", "readDataSources"})
public class MybatisConfiguration extends MybatisAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(MybatisConfiguration.class);

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactorys() throws Exception {
        log.debug("-------------------- 重载父类 sqlSessionFactory init ---------------------");
//        SqlSessionFactory sessionFactory = super.sqlSessionFactory(roundRobinDataSouceProxy());
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(roundRobinDataSouceProxy());
        log.debug("-------------------- sqlSessionFactoryBean.setDataSource(roundRobinDataSouceProxy()); ---------------------");
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:/mybatis/*.xml"));

        //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties props = new Properties();
        props.setProperty("reasonable", "true");
        props.setProperty("supportMethodsArguments", "true");
        props.setProperty("returnPageInfo", "check");
        props.setProperty("params", "count=countSql");
        pageHelper.setProperties(props);
        //添加插件  
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper});

        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    @Order(2)
    public PlatformTransactionManager transactionManager() {
        //boolean a =SpringContextUtil.getBean("roundRobinDataSouceProxy")==roundRobinDataSouceProxy();
        return new DataSourceTransactionManager(roundRobinDataSouceProxy());
    }

    /**
     * 有多少个数据源就要配置多少个bean
     *
     * @return
     */
    @Bean(name = "roundRobinDataSouceProxy")
    public AbstractRoutingDataSource roundRobinDataSouceProxy() {
        MyAbstractRoutingDataSource proxy = new MyAbstractRoutingDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        DataSource writeDataSource = SpringContextUtil.getBean("writeDataSource");
        // 写
        targetDataSources.put(DataSourceTypeEnum.write.getType(), writeDataSource);
        Map<String, DataSource> readsMap = SpringContextUtil.getBean("readDataSources");
        for (Map.Entry<String, DataSource> entry : readsMap.entrySet()) {
            targetDataSources.put(entry.getKey(), entry.getValue());
        }
        proxy.setDefaultTargetDataSource(writeDataSource);
        proxy.setTargetDataSources(targetDataSources);
        return proxy;
    }

}