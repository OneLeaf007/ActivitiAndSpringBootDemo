package com.activiti.demo.controller;


import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activitiTask")
public class TaskController {

    @GetMapping("/completed")
    public String compeletedTask(@RequestParam(value = "id",required = false) String id){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService  taskService = processEngine.getTaskService();
        taskService.complete("12502");


        return null;
    }



}
