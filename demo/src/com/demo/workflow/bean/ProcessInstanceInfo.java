package com.demo.workflow.bean;

import java.io.Serializable;
import java.util.Date;

public class ProcessInstanceInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7266451830544369369L;
	private String procInstId;
	private String businessKey;
	private String procDefId;
	private Date startTime;
	private Date endTime;
	private String duration;
	private String startUserId;
	private String startUserName;
	private String procInstName;
	private boolean activi;
	private String procDefName;
	private String procDefKey;
	private String curActivityId;
	private String curActivityName;
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
	public String getProcDefId() {
		return procDefId;
	}
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getStartUserId() {
		return startUserId;
	}
	public void setStartUserId(String startUserId) {
		this.startUserId = startUserId;
	}
	public String getStartUserName() {
		return startUserName;
	}
	public void setStartUserName(String startUserName) {
		this.startUserName = startUserName;
	}
	public String getProcInstName() {
		return procInstName;
	}
	public void setProcInstName(String procInstName) {
		this.procInstName = procInstName;
	}
	public boolean isActivi() {
		return activi;
	}
	public void setActivi(boolean activi) {
		this.activi = activi;
	}
	public String getProcDefName() {
		return procDefName;
	}
	public void setProcDefName(String procDefName) {
		this.procDefName = procDefName;
	}
	public String getProcDefKey() {
		return procDefKey;
	}
	public void setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
	}
	public String getCurActivityId() {
		return curActivityId;
	}
	public void setCurActivityId(String curActivityId) {
		this.curActivityId = curActivityId;
	}
	public String getCurActivityName() {
		return curActivityName;
	}
	public void setCurActivityName(String curActivityName) {
		this.curActivityName = curActivityName;
	}
	@Override
	public String toString() {
		return "ProcessInstanceInfo [procInstId=" + procInstId
				+ ", businessKey=" + businessKey + ", procDefId=" + procDefId
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", duration=" + duration + ", startUserId=" + startUserId
				+ ", startUserName=" + startUserName + ", procInstName="
				+ procInstName + ", activi=" + activi + ", procDefName="
				+ procDefName + ", procDefKey=" + procDefKey
				+ ", curActivityId=" + curActivityId + ", curActivityName="
				+ curActivityName + "]";
	}
}
