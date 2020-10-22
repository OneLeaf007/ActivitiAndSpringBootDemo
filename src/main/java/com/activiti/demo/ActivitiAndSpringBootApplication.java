package com.activiti.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.*"})
@MapperScan(value = "com.activiti.demo")
public class ActivitiAndSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiAndSpringBootApplication.class, args);
    }

}
