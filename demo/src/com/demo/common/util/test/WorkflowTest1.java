package com.demo.common.util.test;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;

public class WorkflowTest1 {
	
	private static RuntimeService runtimeService;
	private static TaskService taskService;
	private static IdentityService identityService;
	
	public static void main(String[] args) {
		initEngine();
		doTest();
	}

	private static void doTest() {
		List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().list();
		System.out.println(processInstances);
	}

	private static void initEngine() {
		ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration()
				  .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE)
				  .setJdbcUrl("jdbc:sqlserver://192.168.8.55:1433;database=De_test;")
				  .setJdbcUsername("sa")
				  .setJdbcPassword("iwwk~A3c")
				  .setJdbcDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver")
				  .setDatabaseSchemaUpdate("true")
				  .buildProcessEngine();
		runtimeService = processEngine.getRuntimeService();
		taskService = processEngine.getTaskService();
		identityService = processEngine.getIdentityService();
	}
}
