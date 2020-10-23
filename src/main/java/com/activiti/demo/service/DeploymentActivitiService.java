package com.activiti.demo.service;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DeploymentActivitiService {
    @Autowired
    private ProcessEngine processEngine;

    public Deployment deploymentActiviti(String reScore ,String name){

        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource(reScore).name(name)
                .deploy();

        return deployment;

    }

    public Deployment DeploymentActivitiInfo(String reScore ,String name){

        System.out.println(processEngine.getRepositoryService().createDeploymentQuery().deploymentId("2501").singleResult());


        return null;

    }
}
