package com.demo.test.workflowtest.bean;

import java.io.Serializable;
import java.util.Date;

public class LeaveApplyInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8931786957657658041L;
	
	private Integer id;
	private String personCode;
	private String lastName;
	private String leaveReason;
	private Date startDate;
	private Date endDate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPersonCode() {
		return personCode;
	}
	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getLeaveReason() {
		return leaveReason;
	}
	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Override
	public String toString() {
		return "LeaveApplyInfo [id=" + id + ", personCode=" + personCode
				+ ", lastName=" + lastName + ", leaveReason=" + leaveReason
				+ ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
}
