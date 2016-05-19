package com.demo.workflow.util;

import java.util.Iterator;
import java.util.Map;

import org.activiti.engine.impl.javax.el.ExpressionFactory;
import org.activiti.engine.impl.javax.el.ValueExpression;
import org.activiti.engine.impl.juel.ExpressionFactoryImpl;
import org.activiti.engine.impl.juel.SimpleContext;

/**
 * el表达式解析
 * @author heshaowei
 *
 */
public class ExpressionUtil {
	
	/**
	 * 用于判断流程走向，返回值必须是布尔型
	 * @param conditionText el表达式 like:${code=='001'}
	 * @param name 变量名称
	 * @param value 变量值
	 * @return
	 */
	public static boolean parseConditionText(String conditionText,String name,Object value){
		ExpressionFactory factory = new ExpressionFactoryImpl();
		SimpleContext context = new SimpleContext();
		context.setVariable(name, factory.createValueExpression(value, String.class));
		ValueExpression e = factory.createValueExpression(context, conditionText, boolean.class);
		return (Boolean) e.getValue(context);
	}
	
	/**
	 * 用于判断流程走向，返回值必须是布尔型
	 * @param conditionText el表达式 like:${code=='001'}
	 * @param params 变量
	 * @return
	 */
	public static boolean parseConditionText(String conditionText,Map<String, Object> params){
		ExpressionFactory factory = new ExpressionFactoryImpl();
		SimpleContext context = new SimpleContext();
		
		Iterator<String> is = params.keySet().iterator();
		while(is.hasNext()){
			String key = is.next();
			Object value = params.get(key);
			context.setVariable(key, factory.createValueExpression(value, String.class));
		}
		
		ValueExpression e = factory.createValueExpression(context, conditionText, boolean.class);
		return (Boolean) e.getValue(context);
	}
}
