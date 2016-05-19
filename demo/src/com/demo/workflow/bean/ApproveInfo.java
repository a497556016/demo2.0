package com.demo.workflow.bean;

import java.io.Serializable;
import java.util.Date;

public class ApproveInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6667711485343700451L;
	private Integer id;
	private String businessKey;
	private String approveCode;
	private String approveName;
	private String approveRole;
	private String lobNumber;
	private String lastName;
	private String type;
	private String remark;
	private Long duration;
	private Date createDate;
	private String createBy;
	private Date lastUpdate;
	private String lastUpdateBy;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBusinessKey() {
		return businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	public String getApproveCode() {
		return approveCode;
	}
	public void setApproveCode(String approveCode) {
		this.approveCode = approveCode;
	}
	public String getApproveName() {
		return approveName;
	}
	public void setApproveName(String approveName) {
		this.approveName = approveName;
	}
	public String getApproveRole() {
		return approveRole;
	}
	public void setApproveRole(String approveRole) {
		this.approveRole = approveRole;
	}
	public String getLobNumber() {
		return lobNumber;
	}
	public void setLobNumber(String lobNumber) {
		this.lobNumber = lobNumber;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	@Override
	public String toString() {
		return "ApproveInfo [id=" + id + ", businessKey=" + businessKey
				+ ", approveCode=" + approveCode + ", approveName="
				+ approveName + ", approveRole=" + approveRole + ", lobNumber="
				+ lobNumber + ", lastName=" + lastName + ", remark=" + remark
				+ ", duration=" + duration + ", createDate=" + createDate
				+ ", createBy=" + createBy + ", lastUpdate=" + lastUpdate
				+ ", lastUpdateBy=" + lastUpdateBy + "]";
	}
}
