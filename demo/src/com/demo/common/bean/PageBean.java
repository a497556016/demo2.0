package com.demo.common.bean;

/**
 * 分页查询实体
 * @author heshaowei
 *
 */
public class PageBean {
	private Integer start = 0;
	private Integer limit = 999999999;
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	
}
