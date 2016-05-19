package com.demo.common.model;
import java.io.Serializable;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class Config implements Serializable {

	/****/
	private Integer id;

	/****/
	private Integer enable;

	/****/
	private java.util.Date createDate;

	/****/
	private java.util.Date modifyDate;

	/****/
	private String name;

	/****/
	private String value;

	/****/
	private String regex;

	/****/
	private String description;

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

	public void setEnable(Integer enable){
		this.enable = enable;
	}

	public Integer getEnable(){
		return this.enable;
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

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setValue(String value){
		this.value = value;
	}

	public String getValue(){
		return this.value;
	}

	public void setRegex(String regex){
		this.regex = regex;
	}

	public String getRegex(){
		return this.regex;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return this.description;
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
		return "Config [id="+id+",enable="+enable+",createDate="+createDate+",modifyDate="+modifyDate+
		",name="+name+",value="+value+",regex="+regex+
		",description="+description+",createBy="+createBy+",modifyBy="+modifyBy+
		"]";
	}
}
