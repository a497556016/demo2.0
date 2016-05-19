package com.demo.common.model;
import java.io.Serializable;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class UserRoleRelation implements Serializable {

	/****/
	private Integer id;

	/****/
	private java.util.Date createDate;

	/****/
	private java.util.Date modifyDate;

	/****/
	private String personCode;

	/****/
	private String roleCode;

	/****/
	private String createBy;

	/****/
	private String modifyBy;



	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}

	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	public void setModifyDate(java.util.Date modifyDate){
		this.modifyDate = modifyDate;
	}

	public java.util.Date getModifyDate(){
		return this.modifyDate;
	}

	public void setPersonCode(String personCode){
		this.personCode = personCode;
	}

	public String getPersonCode(){
		return this.personCode;
	}

	public void setRoleCode(String roleCode){
		this.roleCode = roleCode;
	}

	public String getRoleCode(){
		return this.roleCode;
	}

	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}

	public String getCreateBy(){
		return this.createBy;
	}

	public void setModifyBy(String modifyBy){
		this.modifyBy = modifyBy;
	}

	public String getModifyBy(){
		return this.modifyBy;
	}

	@Override
	public String toString() {
		return "UserRoleRelation [id="+id+",createDate="+createDate+",modifyDate="+modifyDate+",personCode="+personCode+
		",roleCode="+roleCode+",createBy="+createBy+",modifyBy="+modifyBy+
		"]";
	}
}
