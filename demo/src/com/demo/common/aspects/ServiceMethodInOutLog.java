package com.demo.common.aspects;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.demo.common.util.StringUtils;

/**
 * 打印service层输入输出日志
 * @author heshaowei
 *
 */
@Aspect
public class ServiceMethodInOutLog {
	private static final Logger LOGGER = Logger.getLogger(ServiceMethodInOutLog.class);
	
	@Pointcut(value = "execution(* com.demo..*.service.*.*(..))")  
    public void pointcut() {} 
	
	@Around(value = "com.demo.common.aspects.ServiceMethodInOutLog.pointcut()")
	public Object doMethod(ProceedingJoinPoint pjp){
		LOGGER.info("--------方法开始："+pjp.toLongString());
		LOGGER.info("--------输入参数："+Arrays.toString(pjp.getArgs()));
		
		Object obj = null;
		try {
			obj = pjp.proceed();
		} catch (Throwable e) {
			LOGGER.error(e.getMessage(),e);
		}
		
		LOGGER.info("--------方法结束："+pjp.toLongString());
		//当返回参数太多时不打印日志，以免影响效率
		String returnParam = StringUtils.toStr(obj);
		LOGGER.info("--------返回参数："+(returnParam.length()>8000?returnParam.substring(0, 8000):returnParam));
		
		return obj;
	}
}
