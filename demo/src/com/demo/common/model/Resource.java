package com.demo.common.model;
import java.io.Serializable;
import java.util.List;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class Resource implements Serializable {

	/****/
	private Integer id;

	/****/
	private Integer sortId;

	/****/
	private java.util.Date createDate;

	/****/
	private java.util.Date modifyDate;

	/****/
	private String code;

	/****/
	private String pCode;

	/****/
	private String text;

	/****/
	private String url;

	/****/
	private String createBy;

	/****/
	private String type;

	/****/
	private Boolean expanded;

	/****/
	private Boolean leaf;

	/****/
	private String modifyBy;
	
	private List<Resource> children;

	public List<Resource> getChildren() {
		return children;
	}

	public void setChildren(List<Resource> children) {
		this.children = children;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSortId() {
		return sortId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public java.util.Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(java.util.Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getpCode() {
		return pCode;
	}

	public void setpCode(String pCode) {
		this.pCode = pCode;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	@Override
	public String toString() {
		return "Resource [id=" + id + ", sortId=" + sortId + ", createDate="
				+ createDate + ", modifyDate=" + modifyDate + ", code=" + code
				+ ", pCode=" + pCode + ", text=" + text + ", url=" + url
				+ ", createBy=" + createBy + ", type=" + type + ", expanded="
				+ expanded + ", leaf=" + leaf + ", modifyBy=" + modifyBy + "]";
	}


}
