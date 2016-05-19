package com.demo.workflow.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author heshaowei
 *
 */
public class TodoTaskInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4110551920793056738L;
	private String id;
	private String name;
	private String taskDefKey;
	private Date createTime;
	private String userId;
	private String userName;
	private String businessKey;
	private String procInstId;
	private String procDefId;
	private Date procStartTime;
	private String procStartUserId;
	private String procStartUserName;
	private String procInstName;
	private String procDefName;
	private String processRemark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTaskDefKey() {
		return taskDefKey;
	}
	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getBusinessKey() {
		return businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	public String getProcInstId() {
		return procInstId;
	}
	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
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
	@Override
	public String toString() {
		return "TodoTaskInfo [id=" + id + ", name=" + name + ", taskDefKey="
				+ taskDefKey + ", createTime=" + createTime + ", userId="
				+ userId + ", userName=" + userName + ", businessKey="
				+ businessKey + ", procInstId=" + procInstId + ", procDefId="
				+ procDefId + ", procStartTime=" + procStartTime
				+ ", procStartUserId=" + procStartUserId
				+ ", procStartUserName=" + procStartUserName
				+ ", procInstName=" + procInstName + "]";
	}
	
}
