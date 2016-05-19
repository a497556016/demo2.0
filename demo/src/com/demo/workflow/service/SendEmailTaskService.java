package com.demo.workflow.service;

import org.activiti.engine.task.Task;

public interface SendEmailTaskService {
	public void notify(Task task,String approvers);
}
