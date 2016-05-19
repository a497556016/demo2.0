package com.demo.common.util.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解配置接口
 * @author heshaowei
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelHeadName {
	String STRING = "string";
	String INTEGER = "integer";
	String DOUBLE = "double";
	String FLOAT = "float";
	String DATE = "date";
	String INT_STRING = "intString";
	
	public String name();
	public String type() default STRING;
	public String fmt() default "yyyy-MM-dd HH:mm:ss";
	/**
	 * 是否为锁定，锁定的单元格限制为单元格的值不可编辑
	 * 导入时验证是否修改
	 * @return
	 */
	public boolean isLocked() default false;
	/**
	 * 验证值
	 * @return
	 */
	public String[] validDatas() default {};
	/**
	 * 从基础数据表中根据类型查询验证数据
	 * @return
	 */
	public String basicValidDatas() default "";
	/**
	 * 自定义查询方法
	 * @return
	 */
	public String validQueryMethod() default "";
	/**
	 * 列宽
	 * @return
	 */
	public int columnWidth() default 6000;
	
	/**
	 * 是否在导入时允许为null，默认为false
	 * @return
	 */
	public boolean allowNull() default false;
	
	/**
	 * 是否在导入时允许为空，默认为true，非必填项
	 * @return
	 */
	public boolean allowEmpty() default true;
	
	/**
	 * 验证的正则表达式，为空时不需要验证
	 * @return
	 */
	public String regex() default "";
}
