package com.activiti.demo.controller;


import com.activiti.demo.service.DeploymentActivitiService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    DeploymentActivitiService deploymentActivitiService;

    /**
     * 流程部署
     * @return
     */
    @GetMapping("/run")
    public Deployment DeploymentActiviti(@RequestParam(value = "name",required = false) String name , HttpServletRequest httpServletRequest) {


        Deployment deployment =deploymentActivitiService.DeploymentActiviti("process/holiday.bpmn",name);

        System.out.println(deployment.getId());
        System.out.println(deployment.getName());
        System.out.println(deployment.getDeploymentTime());
        System.out.println(deployment.getCategory());
        return deployment;
    }

    /**
     * 查询流程的定义部署信息
     * @param dpid
     * @return
     */
    @GetMapping("/query")
    public Map DeploymentActivitiInfo(@RequestParam(value = "dpid",required = false) String dpid){

        //System.out.println(processEngine.getRepositoryService().createDeploymentQuery().deploymentId("2501").singleResult());
        Deployment deployment =  processEngine.getRepositoryService().createDeploymentQuery().deploymentId("2501").singleResult();
        List<Deployment> list =processEngine.getRepositoryService().createDeploymentQuery().list();
        System.out.println(deployment.getId());
        System.out.println(deployment.getName());
        System.out.println(deployment.getDeploymentTime());
        System.out.println(deployment.getTenantId());
        Map map =new HashMap();
        map.put("id",deployment.getId());
        map.put("name",deployment.getName());
        map.put("time",deployment.getDeploymentTime());
        map.put("uid",deployment.getTenantId());
        System.out.println("list==========================="+list);
        return map;

    }

    /**
     * 开始一个流程 并根据流程定义 xml文件等部署流转任务
     * @param dpid
     * @return
     */
    @GetMapping("/startAnWorkFlow")
    public Map Deployment(@RequestParam(value = "dpid",required = false) String dpid){
        Deployment deployment =  processEngine.getRepositoryService().createDeploymentQuery()
                .deploymentId("2501")
                .singleResult();
        String key= processEngine.getRepositoryService().
                createProcessDefinitionQuery().
                deploymentId("2501").
                singleResult().
                getKey();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key);
        System.out.println(processInstance);


        return null;

    }

}


    /**
     * 流程定义部署的进本步骤
     */
    // 1.创建ProcesEngine对   //ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
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



