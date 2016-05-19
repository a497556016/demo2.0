package com.demo.common.util.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class WorkflowTest {
	private static ApplicationContext context;
	
	private static RuntimeService runtimeService;
	private static TaskService taskService;
	private static IdentityService identityService;
	private static RepositoryService repositoryService;
	
	public static void main(String[] args) {
		initSpring();
		initService();
		doTest1();
	}

	private static void initSpring() {
		String[] locations = new String[]{
				
				"spring/applicationContext-dataSource.xml",
				"spring/applicationContext-activiti.xml"
		};
		context = new ClassPathXmlApplicationContext(locations);
	}

	private static void initService() {
		if(null==context){
			System.out.println("spring配置没有加载成功！");
			return;
		}
		runtimeService = (RuntimeService) context.getBean("runtimeService");
		taskService = (TaskService) context.getBean("taskService");
		repositoryService = (RepositoryService) context.getBean("repositoryService");
	}
	
	private static void doTest1(){
		Deployment deployment = repositoryService.createDeployment().addClasspathResource("workflowResources/MyProcess.bpmn").deploy();
		System.out.println(deployment.getName());
		
		Map<String, Object> map = new HashMap<>();
		map.put("startUser", "12345");
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess", map);
		
		System.out.println(processInstance);
		
		List<Task> curTasks = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
		
		for (Task task : curTasks) {
			System.out.println(task);
			map.put("isend", "false");
//			map.put("user1", "admin");
			taskService.complete(task.getId(), map);
		}
		
		curTasks = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
		for (Task task : curTasks) {
			taskService.addCandidateUser(task.getId(), "qwer");
			System.out.println(task);
		}
		
		
	}

	private static void doTest() {
		List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().list();
		
		String startUser = "0000031415";
		String businessKey = "T"+System.currentTimeMillis();
		
		Map<String, Object> args = new HashMap<>();
		args.put("startUser", startUser);
		args.put("businessKey", businessKey);
		//设置发起人
		identityService.setAuthenticatedUserId(startUser);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leaveProcess",businessKey, args);
		
		//查询当前任务
		Task curTask = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		System.out.println("当前任务为："+curTask);
		List<IdentityLink> identityLinks = taskService.getIdentityLinksForTask(curTask.getId());
		for (IdentityLink identityLink : identityLinks) {
			System.out.println(identityLink.getUserId());
		}
		
		//完成任务
		args.put("isJumpUsertask1", "1");
		taskService.complete(curTask.getId(), args);
		//查询当前任务
		curTask = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		taskService.addCandidateUser(curTask.getId(), "0000031415");
		System.out.println("当前任务为："+curTask);
		
		List<Task> tasks = taskService.createTaskQuery().taskCandidateUser("0000031415").list();
		for (Task task : tasks) {
			System.out.println("0000031415的待办任务有："+task);
		}
		
		
	}
}
