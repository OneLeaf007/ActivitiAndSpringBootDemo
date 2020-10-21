package com.activiti.demo;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication(scanBasePackages = {"com.*"})
@MapperScan(value = "com.activiti.demo")
public class ActivitiAndSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiAndSpringBootApplication.class, args);
    }
    @Bean(name = "processEngine")
    public ProcessEngine CreateProcessEngine(){
            //创建一个流程成引擎对像
            ProcessEngineConfiguration conf = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
            //设置数据源
            conf.setJdbcDriver("com.mysql.jdbc.Driver");
            conf.setJdbcUrl("jdbc:mysql://localhost:3306/activiti?serverTimezone=UTC");
            conf.setJdbcUsername("root");
            conf.setJdbcPassword("123456");
            //设置自动创建表
            conf.setDatabaseSchemaUpdate("true");
            //在创建引擎对象的时候自动创建表
            ProcessEngine processEngine = conf.buildProcessEngine();
        System.out.println("ProcessEngine对象======="+processEngine);
        return processEngine;
    }
}
