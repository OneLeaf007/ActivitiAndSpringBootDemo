package com.activiti.demo.controller;


import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
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
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(name).active()
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

}
