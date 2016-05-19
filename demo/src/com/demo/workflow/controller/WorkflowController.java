package com.demo.workflow.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.common.controller.BaseController;
import com.demo.common.response.BaseResponse;
import com.demo.common.response.PageResponse;
import com.demo.common.util.RequestParams;
import com.demo.common.util.StringUtils;
import com.demo.common.util.UserInfoUtil;
import com.demo.workflow.bean.ActivityInfo;
import com.demo.workflow.bean.ApproveInfo;
import com.demo.workflow.bean.DoneTaskInfo;
import com.demo.workflow.bean.ProcessInstanceInfo;
import com.demo.workflow.bean.TodoTaskInfo;
import com.demo.workflow.service.WorkflowService;

@Controller
@RequestMapping("/workflow")
public class WorkflowController extends BaseController {
	private static final Logger LOGGER = Logger.getLogger(WorkflowController.class);
	
	@Autowired
	private WorkflowService workflowService;
	
	@RequestMapping("/getProcVariables")
	@ResponseBody
	public BaseResponse<Map<String, Object>> getProcVariables(HttpServletRequest request,HttpServletResponse response,
			@RequestParam String procInstId){
		Map<String, Object> variables = workflowService.getProcVariables(procInstId);
		BaseResponse<Map<String, Object>> baseResponse = new BaseResponse<>();
		baseResponse.setResultInfo(variables);
		baseResponse.setSuccess(true);
		return baseResponse;
	}
	
	@RequestMapping("/getNextTask")
	@ResponseBody
	public BaseResponse<List<Map<String, Object>>> getNextTask(HttpServletRequest request,HttpServletResponse response){
		String taskId = request.getParameter("taskId");
		String variables = request.getParameter("variables");
		Map<String, Object> params = gson.fromJson(variables, HashMap.class);
		List<Map<String, Object>> tasks = workflowService.getNextTask(taskId, params);
		BaseResponse<List<Map<String, Object>>> baseResponse = new BaseResponse<>();
		baseResponse.setResultInfo(tasks);
		baseResponse.setSuccess(true);
		return baseResponse;
	}
	
	@RequestMapping("/completeTask")
	public void completeTask(HttpServletRequest request,HttpServletResponse response){
		String taskId = request.getParameter("taskId");
		String type = request.getParameter("type");
		String message = request.getParameter("message");
		String taskParams = request.getParameter("taskParams");
		String approvers = request.getParameter("approvers");
		Map<String, Object> params = gson.fromJson(taskParams, HashMap.class);
		boolean success = workflowService.completeTask(taskId, approvers, type, message, params);
		
		Map<String, Object> reMap = new HashMap<>();
		reMap.put("success", success);
		print(reMap);
	}
		
	
	@RequestMapping("/todoWork_queryUserTodoTasklist")
	@ResponseBody
	public PageResponse<TodoTaskInfo> queryUserTodoTasklist(HttpServletRequest request,HttpServletResponse response){
		int start = Integer.parseInt(request.getParameter("start"));
		int limit = Integer.parseInt(request.getParameter("limit"));
		String userId = request.getParameter("userId");
		if(StringUtils.isEmpty(userId)){
			userId = UserInfoUtil.getUserId();
		}
		userId = StringUtils.appenderLength(userId, 10);
		LOGGER.info("开始查询用户："+userId+"的待办任务!");
		
		Map<String, Object> params = RequestParams.packageParam(request);
		params.put("userId", userId);
		 
		PageResponse<TodoTaskInfo> pageResponse = workflowService.queryUserTodoTasklist(params,start,limit);
		return pageResponse;
	}
	
	@RequestMapping("/doneWork_queryUserDoneTasklist")
	@ResponseBody
	public PageResponse<DoneTaskInfo> queryUserDoneTasklist(HttpServletRequest request,HttpServletResponse response){
		int start = Integer.parseInt(request.getParameter("start"));
		int limit = Integer.parseInt(request.getParameter("limit"));
		String userId = request.getParameter("userId");
		if(StringUtils.isEmpty(userId)){
			userId = UserInfoUtil.getUserId();
		}
		userId = StringUtils.appenderLength(userId, 10);
		LOGGER.info("开始查询用户："+userId+"的已办任务!");
		
		Map<String, Object> params = RequestParams.packageParam(request);
		params.put("userId", userId);
		 
		PageResponse<DoneTaskInfo> pageResponse = workflowService.queryUserDoneTasklist(params,start,limit);
		return pageResponse;
	}
	
	@RequestMapping("/procMana_jumpTask")
	@ResponseBody
	public BaseResponse<?> jumpTask(HttpServletRequest request,HttpServletResponse response,
			@RequestParam String procInstId,@RequestParam String activityId,@RequestParam String userId) throws Exception{
		LOGGER.info("---------跳转任务，任务参数:"+new String[]{procInstId,activityId,userId});
		userId = StringUtils.appenderLength(userId, 10);
		BaseResponse baseResponse = new BaseResponse<>();
		workflowService.jumpTask(procInstId, activityId, userId);
		baseResponse.setSuccess(true);
		
		LOGGER.info("---------跳转任务完成！");
		return baseResponse;
	}
	
	@RequestMapping("/procMana_queryProcessList")
	@ResponseBody
	public PageResponse<ProcessInstanceInfo> queryProcessList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> params = RequestParams.packageParam(request);
		LOGGER.info("---------查询流程信息输入参数："+params);
		PageResponse<ProcessInstanceInfo> pageResponse = workflowService.queryProcessList(params);
		return pageResponse;
	}
	
	@RequestMapping("/procMana_getProcAllActivity")
	@ResponseBody
	public PageResponse<ActivityInfo> getProcAllActivity(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String procInstId = request.getParameter("procInstId");
		
		LOGGER.info("---------查询流程节点输入参数："+procInstId);
		
		PageResponse<ActivityInfo> pageResponse = workflowService.getAllActivity(procInstId);
		
		return pageResponse;
	}
	
	@RequestMapping("/procMana_deleteProcInst")
	@ResponseBody
	public BaseResponse<?> deleteProcInst(HttpServletRequest request,HttpServletResponse response,
			String procInstId){
		BaseResponse<?> baseResponse = workflowService.deleteProcInst(procInstId);
		return baseResponse;
	}
	
	@RequestMapping("/exportProcessImg")
	public void exportProcessImg(HttpServletRequest request,HttpServletResponse response){
		String procDefKey = request.getParameter("procDefKey");
		workflowService.exportProcessImg(procDefKey,response);
	}


	
	@RequestMapping("/queryApproveInfo")
	@ResponseBody
	public PageResponse<ApproveInfo> queryApproveInfo(HttpServletRequest request,HttpServletResponse response,
			@RequestParam String businessKey){
		PageResponse<ApproveInfo> pageResponse = workflowService.queryApproveInfo(businessKey);
		return pageResponse;
	}
	
	@RequestMapping("/getUserTaskCount")
	@ResponseBody
	public long getTodoTaskCount(HttpServletRequest request,HttpServletResponse response){
		String userId = UserInfoUtil.getUserId();
		long count = workflowService.getTodoTaskCount(userId);
		return count;
	}
}
