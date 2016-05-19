package com.demo.common.model;
import java.io.Serializable;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class BasicInfo implements Serializable {

	/****/
	private Integer id;

	/****/
	private Integer seq;

	/****/
	private java.util.Date createDate;

	/****/
	private java.util.Date modifyDate;

	/****/
	private String type;

	/****/
	private String code;

	/****/
	private String name;

	/****/
	private String remark;

	/****/
	private String createBy;

	/****/
	private String modifyBy;

	/****/
	private String enable;



	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setSeq(Integer seq){
		this.seq = seq;
	}

	public Integer getSeq(){
		return this.seq;
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

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return this.type;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return this.code;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
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

	public void setEnable(String enable){
		this.enable = enable;
	}

	public String getEnable(){
		return this.enable;
	}

	@Override
	public String toString() {
		return "BasicInfo [id="+id+",seq="+seq+",createDate="+createDate+",modifyDate="+modifyDate+
		",type="+type+",code="+code+",name="+name+
		",remark="+remark+",createBy="+createBy+",modifyBy="+modifyBy+
		",enable="+enable+"]";
	}
}
