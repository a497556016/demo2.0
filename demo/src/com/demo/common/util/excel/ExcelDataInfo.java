package com.demo.common.util.excel;

import java.io.Serializable;
import java.util.List;

/**
 * excel导入数据实体
 * @author heshaowei
 *
 */
public class ExcelDataInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4764123292631133529L;
	/**
	 * 页签名称
	 */
	private String key;
	/**
	 * 页签数据
	 */
	private List<?> listData;
	public ExcelDataInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ExcelDataInfo(String key, List<?> listData) {
		super();
		this.key = key;
		this.listData = listData;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List<?> getListData() {
		return listData;
	}
	public void setListData(List<?> listData) {
		this.listData = listData;
	}
	@Override
	public String toString() {
		return "ExcelDataInfo [key=" + key + ", listData=" + listData + "]";
	}
}
