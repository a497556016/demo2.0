package com.demo.common.model;
import java.io.Serializable;
import java.util.List;

import com.demo.common.bean.PageBean;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class UserInfo extends PageBean implements Serializable {

	/****/
	private Integer id;

	/****/
	private java.util.Date createDate;

	/****/
	private java.util.Date modifyDate;

	/****/
	private String personCode;

	/****/
	private String lastName;

	/****/
	private String password;

	/****/
	private String email;

	/****/
	private String createBy;

	/****/
	private String locked;

	/****/
	private String modifyBy;
	
	/**用户拥有的权限资源**/
	private List<Resource> resources;
	
	/**
	 * 用户拥有的角色
	 */
	private List<Role> roles;


	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

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

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return this.lastName;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return this.password;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return this.email;
	}

	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}

	public String getCreateBy(){
		return this.createBy;
	}

	public void setLocked(String locked){
		this.locked = locked;
	}

	public String getLocked(){
		return this.locked;
	}

	public void setModifyBy(String modifyBy){
		this.modifyBy = modifyBy;
	}

	public String getModifyBy(){
		return this.modifyBy;
	}

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", createDate=" + createDate
				+ ", modifyDate=" + modifyDate + ", personCode=" + personCode
				+ ", lastName=" + lastName + ", password=" + password
				+ ", email=" + email + ", createBy=" + createBy + ", locked="
				+ locked + ", modifyBy=" + modifyBy + ", resources="
				+ resources + ", roles=" + roles + "]";
	}
}
