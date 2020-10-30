package com.activiti.demo.service;

import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;

/**
 * @ClassName JumpCmd
 * @Description TODO
 * @Author 兰陵笑笑生
 * @Date 2020/10/30 13:36
 * @Version 1.0
 */
public class JumpCmd implements Command<ExecutionEntity> {
    private String processInstanceId;
    private String activityId;
    public static final String REASION_DELETE = "deleted";


    public JumpCmd(String processInstanceId, String activityId) {
        this.processInstanceId = processInstanceId;
        this.activityId = activityId;
    }
    @Override
    public ExecutionEntity execute(CommandContext commandContext) {
        ExecutionEntity executionEntity = commandContext.getExecutionEntityManager().findExecutionById(processInstanceId);
        executionEntity.destroyScope(REASION_DELETE);
        ProcessDefinitionImpl processDefinition = executionEntity.getProcessDefinition();
        ActivityImpl activity = processDefinition.findActivity(activityId);
        executionEntity.executeActivity(activity);
        return executionEntity;
    }
}
