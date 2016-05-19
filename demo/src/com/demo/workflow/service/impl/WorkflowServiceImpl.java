package com.demo.workflow.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.common.dao.BaseDao;
import com.demo.common.response.BaseResponse;
import com.demo.common.response.PageResponse;
import com.demo.common.service.CommonService;
import com.demo.common.util.PropertiesHolder;
import com.demo.common.util.UserInfoUtil;
import com.demo.workflow.bean.ActGeBytearray;
import com.demo.workflow.bean.ActivityInfo;
import com.demo.workflow.bean.ApproveInfo;
import com.demo.workflow.bean.DoneTaskInfo;
import com.demo.workflow.bean.ProcessInstanceInfo;
import com.demo.workflow.bean.TodoTaskInfo;
import com.demo.workflow.mapper.WorkflowMapper;
import com.demo.workflow.service.JumpTaskCmd;
import com.demo.workflow.service.SendEmailTaskService;
import com.demo.workflow.service.WorkflowService;
import com.demo.workflow.util.ExpressionUtil;

@Service
public class WorkflowServiceImpl implements WorkflowService {
	private static final Logger LOGGER = Logger.getLogger(WorkflowServiceImpl.class);
	
	/**
	 * 流程参数中流程启动人的参数名称
	 */
	public static final String PROC_PARAM_NAME_START_USER = "startUser";
	
	/**
	 * 流程参数中流程号的参数名称
	 */
	public static final String PROC_PARAM_NAME_BUSINESS_KEY = "businessKey";
	
	/**
	 * 流程参数中流程名称的参数名称
	 */
	public static final String PROC_PARAM_NAME_PROCESS_NAME = "processName";
	
	/**
	 * 流程参数中流程摘要的参数名称
	 */
	public static final String PROC_PARAM_NAME_PROCESS_REMARK = "processRemark";
	/**
	 * 第一步跳过的任务类型标记名称
	 */
	public static final String IS_JUMP_START_TASK = "isJumpStartTask";
	
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private FormService formService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private ManagementService managementService;
	@Autowired
	private IdentityService identityService;

	@Autowired
	private SendEmailTaskService sendEmailTaskService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private WorkflowMapper workflowMapper;
	
	public ProcessInstance startProcess(String processDefinitionId,String businessKey,String startUserId,String processName,String processRemark,Map<String, Object> params){
		
		//判断流程是否以存在
		List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).list();
		if(null!=processInstances&&processInstances.size()>0){
			LOGGER.debug("流程已经存在，尝试直接完成任务!",new RuntimeException("流程已经存在，尝试直接完成任务!"));
			try {
				List<Task> tasks = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).list();
				for (Task task : tasks) {
					if(null!=params.get("approvers")){
						completeTask(task.getId(), params.get("approvers").toString(), null, null, params);
					}else{
						completeTask(task.getId(), null, null, params);
					}
				}
			} catch (Exception e) {
				LOGGER.error("无法完成当前任务!", e);
				return null;
			}
			return processInstances.get(0);
		}
		
		//设置发起人
		identityService.setAuthenticatedUserId(startUserId);
				
		//启动流程
		if(null==params){
			params = new HashMap<>();
		}
		params.put(PROC_PARAM_NAME_START_USER, startUserId);
		params.put(PROC_PARAM_NAME_BUSINESS_KEY, businessKey);
		params.put(PROC_PARAM_NAME_PROCESS_NAME, processName);
		params.put(PROC_PARAM_NAME_PROCESS_REMARK, processRemark);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionId, businessKey, params);
		
		//跳过流程的第一步填写表单环节
		try {
			Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
			task.setCategory(IS_JUMP_START_TASK);
			taskService.saveTask(task);
			if(null!=params.get("approvers")){
				completeTask(task.getId(), params.get("approvers").toString(), null, null, params);
			}else{
				completeTask(task.getId(), null, null, params);
				taskService.complete(task.getId(), params);
			}
		} catch (Exception e) {
			LOGGER.error("查询流程的第一步任务发生异常："+e.getMessage()+"，没有正常跳过第一步任务！", e);
		}
		
		//设置流程名称
		runtimeService.setProcessInstanceName(processInstance.getId(), processName);
		
		return processInstance;
	}
	
	@Override
	public boolean completeTask(String taskId,String type,String message,Map<String, Object> params){
		return completeTask(taskId, null, type, message, params);
	}
	
	@Override
	public boolean completeTask(String taskId,String approvers,String type,String message,Map<String, Object> params){
		boolean resultFlag = false;
		
		Task task = null;
		try{
			task = taskService.createTaskQuery().taskId(taskId).singleResult();
		}catch(Exception e){
			LOGGER.error("查询任务Id为："+taskId+"的任务失败！", e);
		}
		
		if(null!=task){
			String processInstanceId = task.getProcessInstanceId();
			if(null!=type&&null!=message){
				taskService.addComment(taskId, processInstanceId, type, message);
			}
			taskService.complete(taskId, params);
			//更新审批意见
			ApproveInfo approveInfo = new ApproveInfo();
			String businessKey = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult().getBusinessKey();
			approveInfo.setBusinessKey(businessKey);
			approveInfo.setApproveCode(task.getTaskDefinitionKey());
			approveInfo.setRemark(message);
			approveInfo.setType(type);
			approveInfo.setLastUpdateBy(UserInfoUtil.getUserId());
			this.updateApproveInfo(approveInfo);
			
			//分配任务审批人
			Task nextTask = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
			if(null!=nextTask){
				String nextTaskId = nextTask.getId();
				String nextTaskDefKey = nextTask.getTaskDefinitionKey();
				boolean needApprover = true;
				if(null==approvers){
					//判断是否已经拥有审批人
					List<IdentityLink> identityLinks = taskService.getIdentityLinksForTask(nextTaskId);
					if(null!=identityLinks&&identityLinks.size()>0){
						needApprover = false;
						StringBuffer approvers1 = new StringBuffer();
						for (IdentityLink identityLink : identityLinks) {
							approvers1.append(identityLink.getUserId()+",");
						}
						if(approvers1.length()>0){
							approvers1.deleteCharAt(approvers1.length()-1);
						}
						approvers = approvers1.toString();
					}else{
						//查询是否拥有最近的已审批任务，将旧的审批人分配至该任务
						List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
						.processInstanceId(processInstanceId)
						.taskDefinitionKey(nextTaskDefKey)
						.orderByHistoricTaskInstanceEndTime().desc().list();
						StringBuffer hisApprovers = new StringBuffer();
						if(null!=historicTaskInstances&&historicTaskInstances.size()>0){
							HistoricTaskInstance historicTaskInstance = historicTaskInstances.get(0);
							LOGGER.info("最近的历史任务是："+historicTaskInstance);
							List<HistoricIdentityLink> historicIdentityLinks = historyService.getHistoricIdentityLinksForTask(historicTaskInstance.getId());
							for (HistoricIdentityLink historicIdentityLink : historicIdentityLinks) {
								String userId = historicIdentityLink.getUserId();
								hisApprovers.append(userId+",");
							}
							if(hisApprovers.length()>0){
								hisApprovers.deleteCharAt(hisApprovers.length()-1);
							}
						}
						approvers = hisApprovers.toString();
						LOGGER.info("当前任务的历史审批人有："+approvers);
					}
				}
				if(needApprover){
					//分配审批人
					taskService.addCandidateUser(nextTaskId, approvers);
				}
				//发送通知邮件
				sendEmailTaskService.notify(nextTask, approvers);
			}
			resultFlag = true;
		}else{
			LOGGER.debug("没有查询到任务ID为："+taskId+"的任务！");
		}
		
		return resultFlag;
	}
	
	private void updateApproveInfo(ApproveInfo approveInfo) {
		int i = workflowMapper.updateApproveInfo(approveInfo);
	}

	@Override
	public void jumpTask(String procInsId, String activityId, String userId) throws Exception{
		
		List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(procInsId).list();
		if(executions.size()>0){
			String executionId = executions.get(0).getId();
			List<String> executionIds = new ArrayList<>();
			for (Execution execution : executions) {
				executionIds.add(execution.getId());
			}
			TaskServiceImpl taskServiceImpl=(TaskServiceImpl)taskService;  
			taskServiceImpl.getCommandExecutor().execute(new JumpTaskCmd(executionIds, activityId));
			for(Task task : taskService.createTaskQuery().executionId(executionId).list()){
				List<IdentityLink> identityLinks = taskService.getIdentityLinksForTask(task.getId());
				//删除当前任务的所有审批人
				for (IdentityLink identityLink : identityLinks) {
					String curUserId = identityLink.getUserId();
					taskService.deleteCandidateUser(task.getId(), curUserId);
				}
				//删除空的审批人
				taskService.deleteCandidateUser(task.getId(), "");
				//添加新的审批人
				if(userId.indexOf(",")>-1){
					String[] userIds = userId.split(",");
					for (String id : userIds) {
						taskService.addCandidateUser(task.getId(), id);
					}
				}else{
					taskService.addCandidateUser(task.getId(), userId);
				}
			}
			
		}else{
			throw new RuntimeException("流程实例id{"+procInsId+"}没有执行中任务实例！");
		}
		
	}

	@Override
	public List<Task> getCurrentTaskByProcessInstanceId(String processInstanceId) {
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
		return tasks;
	}

	@Override
	public PageResponse<TodoTaskInfo> queryUserTodoTasklist(Map<String, Object> params,int start,int limit) {
		PageResponse<TodoTaskInfo> pageResponse = new PageResponse<>();
		List<TodoTaskInfo> taskInfos = null;
		long total = 0;
		params.put("start", start);
		params.put("limit", limit);
		taskInfos = workflowMapper.queryUserTodoTaskList(params);
		total = workflowMapper.queryUserTodoTaskCount(params);
		
		LOGGER.info("查询结果："+taskInfos);
		
		pageResponse.setRows(taskInfos);
		pageResponse.setTotal(total);
		return pageResponse;
	}
	
	@Override
	public PageResponse<ActivityInfo> getAllActivity(String procInsId) throws Exception{
		PageResponse<ActivityInfo> pageResponse = new PageResponse<>();
		
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(procInsId).singleResult();
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(processInstance.getProcessDefinitionId());  
		  
		List<ActivityImpl> activitiList = def.getActivities();
		List<ActivityInfo> activityInfos = new ArrayList<>();
		for (ActivityImpl activityImpl : activitiList) {
			if(activityImpl.getProperty("type").equals("userTask")){
				ActivityInfo activityInfo = new ActivityInfo();
				activityInfo.setId(activityImpl.getId());
				activityInfo.setName(activityImpl.getProperty("name").toString());
				activityInfos.add(activityInfo);
			}
		}
		
		LOGGER.info("查询结果:"+activityInfos);
		
		pageResponse.setRows(activityInfos);
		pageResponse.setTotal(activityInfos.size());
		return pageResponse;
	}

	@Override
	public PageResponse<ProcessInstanceInfo> queryProcessList(
			Map<String, Object> params) {
		PageResponse<ProcessInstanceInfo> pageResponse = new PageResponse<>();
		
		List<ProcessInstanceInfo> instanceInfos = new ArrayList<>();
		long total = 0;
		instanceInfos = workflowMapper.queryProcessList(params);
		total = workflowMapper.queryProcessCount(params);
		
		pageResponse.setRows(instanceInfos);
		pageResponse.setTotal(total);
		return pageResponse;
	}

	@Override
	public BaseResponse<?> deleteProcInst(String procInstId) {
		BaseResponse<?> baseResponse = new BaseResponse<>();
		List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().processInstanceId(procInstId).list();
		String businessKey = "";
		if(processInstances.size()>0){
			businessKey = processInstances.get(0).getBusinessKey();
			runtimeService.deleteProcessInstance(procInstId, "customer_delete");
			historyService.deleteHistoricProcessInstance(procInstId);
		}else{
			List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery().processInstanceId(procInstId).list();
			businessKey = historicProcessInstances.size()>0?historicProcessInstances.get(0).getBusinessKey():"";
			historyService.deleteHistoricProcessInstance(procInstId);
		}
		//查询该流程的所有任务进行删除
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(procInstId).list();
		for (Task task : tasks) {
			taskService.deleteTask(task.getId(), "customer_delete");
		}
		List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery().processInstanceId(procInstId).list();
		for (HistoricTaskInstance historicTaskInstance : historicTaskInstances) {
			historyService.deleteHistoricTaskInstance(historicTaskInstance.getId());
		}
		if(!"".equals(businessKey)){
			//删除审批意见，删除业务表单数据
			workflowMapper.deleteApproveInfoByBusinessKey(businessKey);
		}
		
		baseResponse.setSuccess(true);
		return baseResponse;
	}

	@Override
	public PageResponse<DoneTaskInfo> queryUserDoneTasklist(
			Map<String, Object> params, int start, int limit) {
		PageResponse<DoneTaskInfo> pageResponse = new PageResponse<>();
		List<DoneTaskInfo> taskInfos = null;
		long total = 0;
		params.put("start", start);
		params.put("limit", limit);
		taskInfos = workflowMapper.queryUserDoneTaskList(params);
		total = workflowMapper.queryUserDoneTaskCount(params);
		
		LOGGER.info("查询结果："+taskInfos);
		
		pageResponse.setRows(taskInfos);
		pageResponse.setTotal(total);
		return pageResponse;
	}

	@Override
	public Map<String, Object> getProcVariables(String procInstId) {
		
		Map<String, Object> variables = new HashMap<>();
		
		List<HistoricVariableInstance> historicVariableInstances = historyService.createHistoricVariableInstanceQuery().processInstanceId(procInstId).list();
		for (HistoricVariableInstance historicVariableInstance : historicVariableInstances) {
			String name = historicVariableInstance.getVariableName();
			Object value = historicVariableInstance.getValue();
			variables.put(name, value);
		}
		
		return variables;
	}
	
	/**
	 * 获取下一个任务的名称
	 */
	@Override
	public List<Map<String, Object>> getNextTask(String curTaskId,Map<String, Object> params){
		List<String> strs = new ArrayList<String>();
		List<Map<String, Object>> resultList = new ArrayList<>();
		
		Task task = taskService.createTaskQuery().taskId(curTaskId).singleResult();

		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(task.getProcessDefinitionId());  
		
		List<ActivityImpl> activitiList = def.getActivities();  //rs是指RepositoryService的实例  
		
		String excId = task.getExecutionId();  
		ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId).singleResult();  
		String activitiId = execution.getActivityId();
		
		//获取流程参数
		Map<String, Object> procVariables = runtimeService.getVariables(excId);
//		params.putAll(procVariables);
		procVariables.putAll(params);

		for(ActivityImpl activityImpl:activitiList){
			String id = activityImpl.getId();
			if(activitiId.equals(id)){
				System.out.println("当前任务："+activityImpl.getProperty("name")); //输出某个节点的某种属性
				List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();//获取从某个节点出来的所有线路
				for(PvmTransition tr:outTransitions){
					PvmActivity ac = tr.getDestination(); //获取线路的终点节点
					System.out.println("下一步任务任务："+ac.getProperty("name"));
					if("exclusiveGateway".equals(ac.getProperty("type"))){
						List<PvmTransition> outTransitions1 = ac.getOutgoingTransitions();
						for (PvmTransition pvmTransition : outTransitions1) {
							String conditionText = (String) pvmTransition.getProperty("conditionText");
							String destId = pvmTransition.getDestination().getId();
							if(conditionText!=null){
								boolean f = ExpressionUtil.parseConditionText(conditionText, procVariables);
								if(f){
									//判断任务审批人是否为空
									Map<String, Object> param = new HashMap<>();
									param.put("taskName", pvmTransition.getDestination().getProperty("name"));
									param.put("taskDefKey", destId);
									resultList.add(param);
									return resultList;
								}
							}
						}
					}else if("parallelGateway".equals(ac.getProperty("type"))){
						List<PvmTransition> outTransitions1 = ac.getOutgoingTransitions();
						for (PvmTransition pvmTransition : outTransitions1) {
							PvmActivity pac = pvmTransition.getDestination();
							if(pac.getProperty("type")!=null&&pac.getProperty("type").toString().contains("Task")){
								Map<String, Object> param = new HashMap<>();
								param.put("taskName", pvmTransition.getDestination().getProperty("name"));
								param.put("taskDefKey", pac.getId());
								resultList.add(param);
							}
							return resultList;
						}
					}else if(ac.getProperty("type")!=null&&ac.getProperty("type").toString().contains("Task")){
						Map<String, Object> param = new HashMap<>();
						param.put("taskName", ac.getProperty("name"));
						param.put("taskDefKey", ac.getId());
						resultList.add(param);
						return resultList;
					}else{
						return resultList;
					}
					
				}
				break;
			}
		}
		return resultList;
	}

	@Override
	public String getResIdByBusinessKey(String businessKey) {
		String resId = null;
		
		if(null==businessKey){
			return resId;
		}
		
		return resId;
	}

	@Override
	public void exportProcessImg(String procDefKey, HttpServletResponse response) {
		ActGeBytearray actGeBytearray = null;
		actGeBytearray = workflowMapper.exportProcessImg(procDefKey+".png");
		if(null==actGeBytearray){
			return;
		}
		byte[] bytes = actGeBytearray.getBytes();
		InputStream imageStream = new ByteArrayInputStream(bytes);
		//输出资源内容到相应对象
		byte[] b = new byte[1024];
		int len;
		try {
			while((len = imageStream.read(b,0,b.length))!=-1){
			    response.getOutputStream().write(b,0,len);
			}
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
		} finally{
			try {
				if(imageStream!=null){
					imageStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

	

	@Override
	public PageResponse<ApproveInfo> queryApproveInfo(String businessKey) {
		PageResponse<ApproveInfo> pageResponse = new PageResponse<>();
		List<ApproveInfo> approveInfos = workflowMapper.queryApproveInfo(businessKey);
		for (ApproveInfo approveInfo : approveInfos) {
			approveInfo.setApproveName(PropertiesHolder.getString("taskName."+approveInfo.getApproveCode()));
		}
		pageResponse.setRows(approveInfos);
		return pageResponse;
	}

	@Override
	public void initApproveInfos(String processName, String businessKey) {
		switch(processName){
			case "leaveProcess" : {
				this.initVrmApproveInfos(businessKey);
				break;
			}
			default : {
				break;
			}
		}
	}

	private void initVrmApproveInfos(String businessKey) {
		String[] approveCodes = {"deliverManager","duManager","hr"};
		List<ApproveInfo> list = new ArrayList<>();
		for (String approveCode : approveCodes) {
			String approveRole = PropertiesHolder.getString("role."+approveCode);
			ApproveInfo approveInfo = new ApproveInfo();
			approveInfo.setBusinessKey(businessKey);
			approveInfo.setApproveCode(approveCode);
			approveInfo.setApproveRole(approveRole);
			approveInfo.setCreateBy(UserInfoUtil.getUserId());
			list.add(approveInfo);
		}
		Map<String, Object> params = new HashMap<>();
		params.put("list", list);
		int i = workflowMapper.saveApproveInfo(params);
	}

	@Override
	public long getTodoTaskCount(String userId) {
		long total = 0;
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		total = workflowMapper.queryUserTodoTaskCount(params);
		return total;
	}

	
}
