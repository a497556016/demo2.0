package com.demo.common.model;
import java.io.Serializable;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class Role implements Serializable {

	/****/
	private Integer id;

	/****/
	private java.util.Date createDate;

	/****/
	private java.util.Date modifyDate;

	/****/
	private String roleCode;

	/****/
	private String roleName;

	/****/
	private String remark;

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

	public void setRoleCode(String roleCode){
		this.roleCode = roleCode;
	}

	public String getRoleCode(){
		return this.roleCode;
	}

	public void setRoleName(String roleName){
		this.roleName = roleName;
	}

	public String getRoleName(){
		return this.roleName;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return this.remark;
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
		return "Role [id="+id+",createDate="+createDate+",modifyDate="+modifyDate+",roleCode="+roleCode+
		",roleName="+roleName+",remark="+remark+",createBy="+createBy+
		",modifyBy="+modifyBy+"]";
	}
}
