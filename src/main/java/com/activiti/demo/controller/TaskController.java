package com.activiti.demo.controller;


import com.activiti.demo.service.JumpCmd;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/activitiTask")
public class TaskController {

    @Autowired
    ProcessEngine processEngine;

    /**
     * 查询用户待办任务
     * @param name
     * @return
     */
    @GetMapping("/queryTask")
    public List<Task> QueryTask(@RequestParam(value = "name",required = false) String name){
        if(name==null){
            name="lisi";
        }
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(name)
                .list();
        for (Task task:tasks
             ) {
            System.out.println(task);
        }

        return tasks;
    }



    // 代办任务 就是ru_task表 肯定能拿到id
    @GetMapping("/completed")
    public String compeletedTask(@RequestParam(value = "id",required = false) String id){
        //id="5003";
        System.out.println(id);
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService  taskService = processEngine.getTaskService();
        Map map =  new HashMap();
        taskService.complete(id,map);
        return "完成任务";
    }

    /**
     * 工作流自由跳转
     * @param taskId 要跳转到的节点名称
     * @return
     */
    @GetMapping("/taskRollback")
    public String taskRollback(@RequestParam ("taskId") String taskId){
        HistoryService historyService = processEngine.getHistoryService();

        RuntimeService runtimeService = processEngine.getRuntimeService();

        RepositoryService repositoryService = processEngine.getRepositoryService();

        ManagementService managementService = processEngine.getManagementService();


        //根据要跳转的任务ID获取其任务
        HistoricTaskInstance hisTask = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        String taskAssignee = hisTask.getAssignee();
        //进而获取流程实例
        ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(hisTask.getProcessInstanceId()).singleResult();
        //取得流程定义
        ProcessDefinitionEntity definition = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(hisTask.getProcessDefinitionId());
        //获取历史任务的Activity
        ActivityImpl hisActivity = definition.findActivity(hisTask.getTaskDefinitionKey());
        //实现跳转

        managementService.executeCommand(new JumpCmd(instance.getId(), hisActivity.getId()));
        return hisTask.getProcessInstanceId();
    }

}
