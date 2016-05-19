package com.demo.workflow.bean;

import java.io.Serializable;
import java.util.Date;

public class DoneTaskInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2339568488366762124L;
	private String procInstId;
	private String businessKey;
	private String procInstName;
	private String procDefName;
	private String processRemark;
	private String procDefId;
	private Date procStartTime;
	private Date procEndTime;
	private String procDuration;
	private String procStartUserId;
	private String procStartUserName;
	private String taskId;
	private String taskDefKey;
	private String taskName;
	private Date taskStartTime;
	private Date taskEndTime;
	private String taskDuration;
	private String taskAcceptPersonId;
	private String taskAcceptPersonName;
	private String curTaskName;
	private String curTaskDefKey;
	public String getProcInstId() {
		return procInstId;
	}
	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	public String getBusinessKey() {
		return businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	public String getProcInstName() {
		return procInstName;
	}
	public void setProcInstName(String procInstName) {
		this.procInstName = procInstName;
	}
	public String getProcDefName() {
		return procDefName;
	}
	public void setProcDefName(String procDefName) {
		this.procDefName = procDefName;
	}
	public String getProcessRemark() {
		return processRemark;
	}
	public void setProcessRemark(String processRemark) {
		this.processRemark = processRemark;
	}
	public String getProcDefId() {
		return procDefId;
	}
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	public Date getProcStartTime() {
		return procStartTime;
	}
	public void setProcStartTime(Date procStartTime) {
		this.procStartTime = procStartTime;
	}
	public Date getProcEndTime() {
		return procEndTime;
	}
	public void setProcEndTime(Date procEndTime) {
		this.procEndTime = procEndTime;
	}
	public String getProcDuration() {
		return procDuration;
	}
	public void setProcDuration(String procDuration) {
		this.procDuration = procDuration;
	}
	public String getProcStartUserId() {
		return procStartUserId;
	}
	public void setProcStartUserId(String procStartUserId) {
		this.procStartUserId = procStartUserId;
	}
	public String getProcStartUserName() {
		return procStartUserName;
	}
	public void setProcStartUserName(String procStartUserName) {
		this.procStartUserName = procStartUserName;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskDefKey() {
		return taskDefKey;
	}
	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Date getTaskStartTime() {
		return taskStartTime;
	}
	public void setTaskStartTime(Date taskStartTime) {
		this.taskStartTime = taskStartTime;
	}
	public Date getTaskEndTime() {
		return taskEndTime;
	}
	public void setTaskEndTime(Date taskEndTime) {
		this.taskEndTime = taskEndTime;
	}
	public String getTaskDuration() {
		return taskDuration;
	}
	public void setTaskDuration(String taskDuration) {
		this.taskDuration = taskDuration;
	}
	public String getTaskAcceptPersonId() {
		return taskAcceptPersonId;
	}
	public void setTaskAcceptPersonId(String taskAcceptPersonId) {
		this.taskAcceptPersonId = taskAcceptPersonId;
	}
	public String getTaskAcceptPersonName() {
		return taskAcceptPersonName;
	}
	public void setTaskAcceptPersonName(String taskAcceptPersonName) {
		this.taskAcceptPersonName = taskAcceptPersonName;
	}
	public String getCurTaskName() {
		return curTaskName;
	}
	public void setCurTaskName(String curTaskName) {
		this.curTaskName = curTaskName;
	}
	public String getCurTaskDefKey() {
		return curTaskDefKey;
	}
	public void setCurTaskDefKey(String curTaskDefKey) {
		this.curTaskDefKey = curTaskDefKey;
	}
	@Override
	public String toString() {
		return "DoneTaskInfo [procInstId=" + procInstId + ", businessKey="
				+ businessKey + ", procInstName=" + procInstName
				+ ", procDefId=" + procDefId + ", procStartTime="
				+ procStartTime + ", procEndTime=" + procEndTime
				+ ", procDuration=" + procDuration + ", procStartUserId="
				+ procStartUserId + ", procStartUserName=" + procStartUserName
				+ ", taskId=" + taskId + ", taskDefKey=" + taskDefKey
				+ ", taskName=" + taskName + ", taskStartTime=" + taskStartTime
				+ ", taskEndTime=" + taskEndTime + ", taskDuration="
				+ taskDuration + ", taskAcceptPersonId=" + taskAcceptPersonId
				+ ", taskAcceptPersonName=" + taskAcceptPersonName
				+ ", curTaskName=" + curTaskName + ", curTaskDefKey="
				+ curTaskDefKey + "]";
	}
}
