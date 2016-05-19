package com.demo.common.bean;

import java.io.Serializable;

/**
 * 下拉框数据实体类
 * @author heshaowei
 *
 * @param <T>
 */
public class ComboBean<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7552654542191405551L;
	private String value;
	private String name;
	private T data;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "ComboBean [value=" + value + ", name=" + name + ", data="
				+ data + "]";
	}
}
