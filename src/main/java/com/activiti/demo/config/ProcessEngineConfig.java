package com.activiti.demo.config;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ProcessEngineConfig {


    @Value("${activiti.datasource.url}")
    private String url;

    @Value("${activiti.datasource.username}")
    private String username;

    @Value("${activiti.datasource.password}")
    private String password;

    @Value("${activiti.datasource.driver-class-name}")
    private String driverClassName;



    @Bean(name = "processEngine")
    public ProcessEngine CreateProcessEngine(){
        //创建一个流程成引擎对像
        ProcessEngineConfiguration conf = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
        //设置数据源
        conf.setJdbcDriver(driverClassName);
        conf.setJdbcUrl(url);
        conf.setJdbcUsername(username);
        conf.setJdbcPassword(password);
        //设置自动创建表
        conf.setDatabaseSchemaUpdate("false");
        //在创建引擎对象的时候自动创建表
        ProcessEngine processEngine = conf.buildProcessEngine();
        System.out.println("ProcessEngine对象======="+processEngine);
        return processEngine;
    }


}
