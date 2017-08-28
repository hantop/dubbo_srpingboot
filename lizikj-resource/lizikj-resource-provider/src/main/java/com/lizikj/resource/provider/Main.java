package com.lizikj.resource.provider;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ComponentScan
@MapperScan({"com.lizikj.fs.dao.mapper"})
@ImportResource({"classpath:xml/dubbo-service.xml"})
public class Main extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer {

    private static final Log log= LogFactory.getLog(Main.class);

    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
        //ApplicationContext ctx = new SpringApplicationBuilder().sources(Main.class).web(false).run(args);

        log.info("文件服务启动成功！");
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
        configurableEmbeddedServletContainer.setPort(8081);
    }
}
