package com.demo.workflow.bean;

import java.io.Serializable;

public class ActivityInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2432648599535649268L;
	private String id;
	private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "ActivityInfo [id=" + id + ", name=" + name + "]";
	}
}
