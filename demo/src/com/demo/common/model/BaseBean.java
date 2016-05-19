package com.demo.common.model;

import java.util.Date;

public class BaseBean {
	private Date createDate;
    
    private String createBy;
    
    private Date modifyDate;
    
    private String modifyBy;

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

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	@Override
	public String toString() {
		return "BaseBean [createDate=" + createDate + ", createBy=" + createBy
				+ ", modifyDate=" + modifyDate + ", modifyBy=" + modifyBy + "]";
	}

}
