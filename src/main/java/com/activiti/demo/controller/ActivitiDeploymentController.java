package com.activiti.demo.controller;


import com.activiti.demo.service.DeploymentActivitiService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;
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

        if(name==null){
            name ="请假审批流程";
        }
        Deployment deployment =deploymentActivitiService.deploymentActiviti("process/holiday.bpmn",name);


        System.out.println(deployment.getId());
        System.out.println(deployment.getName());
        System.out.println(deployment.getDeploymentTime());
        System.out.println(deployment.getCategory());
        return deployment;
    }

    /**
     * 查询流程的定义信息列表（将来传递给页面进行审核选择的列表）
     * @param dpid
     * @return
     */
    @GetMapping("/query")
    public List<ProcessDefinition>  DeploymentActivitiInfo(@RequestParam(value = "dpid",required = false) String dpid){

        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().active().latestVersion().list();
        for (ProcessDefinition processDefinition: list) {
            System.out.println("ProcessDefinition=========" +processDefinition);
            System.out.println(processDefinition.getName());
            System.out.println(processDefinition.getKey());
            System.out.println(processDefinition.getDeploymentId());

        }
        return null;
    }

    /**
     * 开始一个流程实例  根据实例流程的id 查询执行id 根据执行id 查询任务id 一个实例流程 同一时间只有一个执行
     * 流程开始第一申请人 任务自动流转（发起成功后自动完成 发起人不需要审批）
     * @param dpid
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/startAnWorkFlow")
    public Map DeploymentInstance(@RequestParam(value = "dpid",required = false) String dpid){

        dpid="holiday:1:4";

        String key= processEngine.getRepositoryService().
                createProcessDefinitionQuery().processDefinitionId(dpid).latestVersion()
                .singleResult()
                .getKey();
        RuntimeService runtimeService = processEngine.getRuntimeService();


        Map map = new HashMap();
        map.put("userId","zhangsan");
        map.put("condtion",5);
        try{
            // 流程发起者提交任务成功后自动完成任务
            System.out.println("提交表单保存成功");
            System.out.println("同步更新系统数据库数据");
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key,"holiday",map);
            Execution execution = runtimeService.createExecutionQuery().processInstanceId(processInstance.getId()).singleResult();

            TaskService taskService = processEngine.getTaskService();

            Task task = taskService.createTaskQuery().executionId(execution.getId()).singleResult();
            taskService.complete(task.getId());

        }catch (Exception e){
            e.printStackTrace();

            return null;
        }

        return null;

    }
    /**
     * 删除流程定义
     *
     *
     */
    @GetMapping("/deleteProcessDefinition")
    public String deleteProcessDefinition(@RequestParam(value = "id",required = false) String id){
        RepositoryService repositoryService =processEngine.getRepositoryService();
        // id为部署id Deployment 的id 第二个参数设置为true 是级联删除 强制停止没有完成的任务
        try{
            repositoryService.deleteDeployment("2501",true);
        }catch (Exception e){
            if(e.getClass().getTypeName().equals(SQLIntegrityConstraintViolationException.class.getTypeName())){
                return "该流程定义正在进行不能删除";
            }
            e.printStackTrace();
            return "删除异常";
        }


        return "成功";
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



