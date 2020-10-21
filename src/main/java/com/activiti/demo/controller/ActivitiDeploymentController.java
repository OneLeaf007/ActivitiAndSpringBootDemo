package com.activiti.demo.controller;


import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 兰陵笑笑生
 * 流程定义的部署 将bpmn文件部署到activiti相关的表中
 *
 *
 *
 */
@RestController
@RequestMapping("/ActivitiDeployment")
public class ActivitiDeploymentController {
    @Autowired
    private ProcessEngine processEngine;

    @GetMapping("/run")
    public String DeploymentActiviti() {
        System.out.println("ProcessEngine对象======="+processEngine);
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("holiday.bpmn")
                .name("请假申请流程")
                .deploy();
        System.out.println(deployment.getId());
        System.out.println(deployment.getName());
        System.out.println(deployment.getDeploymentTime());
        System.out.println(deployment.getCategory());
        return deployment.toString();
    }

}


    /**
     * 流程定义部署的进本步骤
     */
    // 1.创建ProcesEngine对象
    //ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    // 2.得到一个RepositaryService对象实例
    //RepositoryService repositoryService = processEngine.getRepositoryService();
    // 3.进行部署



    /**
     * 创建 1.Deployment对象 2.添加数据源 3.调用方法进行部署
     * 注意 deployment对象的id自动生成
     */
//    Deployment deployment = repositoryService.createDeployment()
//            .addClasspathResource("holiday.bpmn")
//            .name("请假申请流程")
//            .deploy();
//    // 4.输出部署的一些信息
//    System.out.println(deployment.getId());
//    System.out.println(deployment.getName());
//    System.out.println(deployment.getDeploymentTime());
//    System.out.println(deployment.getCategory());
    /**
     * 流程定义部署 主要操作的表 1.act_ge_bytearray  保存bpmn png 文件内容
     *                        2.act_re_deployment 保存部署流程定义的部署信息
     *                        3.act_re_procdef    流程定义的详细信息（图片，xml文件上的信息）
     *                        *预计页面展示的流程列表应该就是流程定义的列表
     *
     */

    /***********************************************
     *
     * 上面是流程定义的部署
     * 真正完成审批流任务有流程定义的实例完成
     * 主要是通过流程定义的key启动流程实例（一个流程定义的对应一条部署信息？）
     * 流程实例的启动详见 ActivitiStartInstance类
     *
     ***********************************************/



