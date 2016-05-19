package com.demo.workflow.mapper;

import java.util.List;
import java.util.Map;

import com.demo.workflow.bean.ActGeBytearray;
import com.demo.workflow.bean.ApproveInfo;
import com.demo.workflow.bean.DoneTaskInfo;
import com.demo.workflow.bean.ProcessInstanceInfo;
import com.demo.workflow.bean.TodoTaskInfo;

public interface WorkflowMapper {

	int updateApproveInfo(ApproveInfo approveInfo);

	List<TodoTaskInfo> queryUserTodoTaskList(Map<String, Object> params);

	long queryUserTodoTaskCount(Map<String, Object> params);

	List<ProcessInstanceInfo> queryProcessList(Map<String, Object> params);

	long queryProcessCount(Map<String, Object> params);

	void deleteApproveInfoByBusinessKey(String businessKey);

	List<DoneTaskInfo> queryUserDoneTaskList(Map<String, Object> params);

	long queryUserDoneTaskCount(Map<String, Object> params);

	ActGeBytearray exportProcessImg(String string);

	List<ApproveInfo> queryApproveInfo(String businessKey);

	int saveApproveInfo(Map<String, Object> params);

}
