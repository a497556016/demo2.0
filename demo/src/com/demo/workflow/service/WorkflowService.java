package com.demo.workflow.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import com.demo.common.response.BaseResponse;
import com.demo.common.response.PageResponse;
import com.demo.workflow.bean.ActivityInfo;
import com.demo.workflow.bean.ApproveInfo;
import com.demo.workflow.bean.DoneTaskInfo;
import com.demo.workflow.bean.ProcessInstanceInfo;
import com.demo.workflow.bean.TodoTaskInfo;

public interface WorkflowService {
	/**
	 * 启动流程
	 * @param processDefinitionId 流程定义id
	 * @param businessKey 流水号
	 * @param startUserId 发起人
	 * @param processName 流程名称
	 * @param processRemark 自定义流程摘要描述，保存在流程参数{processRemark}里
	 * @param params 流程参数
	 * @return
	 */
	public ProcessInstance startProcess(String processDefinitionId,String businessKey,String startUserId,String processName,String processRemark,Map<String, Object> params);
	
	/**
	 * 审批完成任务
	 * @param taskId 任务id
	 * @param type 审批意见类型 可空
	 * @param message 审批意见 可空
	 * @param params 任务参数
	 * @return
	 */
	public boolean completeTask(String taskId,String type,String message,Map<String, Object> params);
	
	/**
	 * 审批完成任务,带下一步任务审批人参数
	 * @param taskId 任务id
	 * @param approvers 任务审批人
	 * @param type 审批意见类型 可空
	 * @param message 审批意见 可空
	 * @param params 任务参数
	 * @return
	 */
	public boolean completeTask(String taskId,String approvers,String type,String message,Map<String, Object> params);

	/**
	 * 任务跳转
	 * @param procInsId
	 * @param activityId
	 * @param userId
	 * @throws Exception
	 */
	public void jumpTask(String procInsId, String activityId, String userId)
			throws Exception;
	
	/**
	 * 查询当前运行中任务
	 * @param id
	 */
	public List<Task> getCurrentTaskByProcessInstanceId(String processInstanceId);

	/**
	 * 查询待办任务
	 * @param params
	 * @return
	 */
	public PageResponse<TodoTaskInfo> queryUserTodoTasklist(Map<String, Object> params,int start,int limit);

	/**
	 * 查询流程的所有节点
	 * @param procInsId
	 * @return
	 * @throws Exception
	 */
	public PageResponse<ActivityInfo> getAllActivity(String procInsId)
			throws Exception;

	/**
	 * 查询流程信息
	 * @param params
	 * @return
	 */
	public PageResponse<ProcessInstanceInfo> queryProcessList(
			Map<String, Object> params);

	/**
	 * 删除流程实例
	 * @param procInstId
	 * @return
	 */
	public BaseResponse<?> deleteProcInst(String procInstId);
	
	/**
	 * 查询用户已办任务
	 * @param params
	 * @param start
	 * @param limit
	 * @return
	 */
	public PageResponse<DoneTaskInfo> queryUserDoneTasklist(
			Map<String, Object> params, int start, int limit);

	/**
	 * 获取流程的所有参赛
	 * @param procInstId
	 * @return
	 */
	public Map<String, Object> getProcVariables(String procInstId);

	/**
	 * 获取下一步任务
	 * @param curTaskId
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getNextTask(String curTaskId,Map<String, Object> params);

	/**
	 * 根据流水号获取源数据的ID
	 * @param businessKey
	 * @return
	 */
	public String getResIdByBusinessKey(String businessKey);

	/**
	 * 导出流程图片
	 * @param procDefKey
	 * @param response
	 */
	public void exportProcessImg(String procDefKey, HttpServletResponse response);

	/**
	 * 查询审批意见
	 * @param businessKey
	 * @return
	 */
	public PageResponse<ApproveInfo> queryApproveInfo(String businessKey);

	/**
	 * 初始化审批意见
	 * @param processName
	 * @param businessKey 
	 */
	public void initApproveInfos(String processName, String businessKey);

	/**
	 * 获取用户代办任务数量
	 * @param userId
	 * @return
	 */
	public long getTodoTaskCount(String userId);

}
