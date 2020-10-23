package com.activiti.demo.controller;


import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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

        /**
         * 把指定的复杂对象属性，按照指定的内容，封装到新的map中
         * @param source 目标对象
         * @param ps     需要封装到map中的属性
         * @return
         */

        return tasks;
    }




    @GetMapping("/completed")
    public String compeletedTask(@RequestParam(value = "id",required = false) String id){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService  taskService = processEngine.getTaskService();
        taskService.complete(id);
        return null;
    }

}
