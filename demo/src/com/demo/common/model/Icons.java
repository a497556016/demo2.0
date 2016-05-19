package com.demo.common.model;
import java.io.Serializable;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class Icons implements Serializable {

	/****/
	private Integer id;

	/****/
	private java.util.Date createDate;

	/****/
	private java.util.Date modifyDate;

	/****/
	private byte[] resource;

	/****/
	private String text;

	/****/
	private String code;

	/****/
	private String resourceId;

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

	public void setResource(byte[] resource){
		this.resource = resource;
	}

	public byte[] getResource(){
		return this.resource;
	}

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return this.text;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return this.code;
	}

	public void setResourceId(String resourceId){
		this.resourceId = resourceId;
	}

	public String getResourceId(){
		return this.resourceId;
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
		return "Icons [id="+id+",createDate="+createDate+",modifyDate="+modifyDate+",resource="+resource+
		",text="+text+",code="+code+",resourceId="+resourceId+
		",createBy="+createBy+",modifyBy="+modifyBy+"]";
	}
}
