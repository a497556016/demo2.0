package com.demo.common.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

/**
 * 继承PropertyPlaceholderConfigurer类，加载properties配置信息
 * @author heshaowei
 *
 */
public class PropertiesHolder extends PropertyPlaceholderConfigurer {
	private static final Logger LOGGER = Logger.getLogger(PropertiesHolder.class);
	
	private static final Properties PROP = new Properties();

	private PropertiesHolder(){}

	/**
     * 重写PropertyPlaceholderConfigurer类中的setLocations方法。<br/>
     * 详细描述：接收一个Resource类型的数组作为参数，将locations标签下值都会被解析成Resource，
     * 而这个resource本身则包含了访问这个resource的方法，在这里resource代表的则是properties文件。
     * 
     * @param locations Resource类型的数组。
     * @see #setLocations(Resource[])
     */
	@Override
	public void setLocations(Resource[] locations) {
		super.setLocations(locations);
		Arrays.sort(locations,new Comparator<Resource>() {
			@Override
			public int compare(Resource o1, Resource o2) {
				try {
                    if (ResourceUtils.JAR_URL_SEPARATOR.equals(o1.getURL().getProtocol())) {
                        return -1;
                    } else {
                        return 1;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return 0;
			}
		});
	}

	/**
     * 覆盖Spring属性文件处理方法processProperties。<br/>
     * 详细描述：1.将属性值获取至静态Map对象中，用于java代码中获取属性值。<br/>
     * @param confFactory ConfigurableListableBeanFactory。
     * @param props Properties。
     * @see #postProcessBeanFactory(ConfigurableListableBeanFactory)
     * @throws BeansException异常，spring内部自定义的异常。
     */
	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		Set<Object> keySet = props.keySet();
		Iterator<Object> i = keySet.iterator();
		while(i.hasNext()){
			Object key = i.next();
			Object value = props.get(key);
			LOGGER.info("加载配置文件信息：key="+key+",value="+value);
			PROP.put(key, value);
		}
		super.processProperties(beanFactoryToProcess, props);
	}
	
	public static String getString(String key){
		LOGGER.info("获取配置元素："+key);
		
		String value = PROP.getProperty(key);
		
		LOGGER.info("配置元素"+key+"的值为："+value);
		return value;
	}
	
	public static Boolean getBoolean(String key){
		LOGGER.info("获取配置元素："+key);
		
		String value = PROP.getProperty(key);
		
		Boolean result = null;
		
		if(StringUtils.isNotEmpty(value)&&("1".equals(value)||"true".equals(value))){
			result = true;
		}else if(StringUtils.isNotEmpty(value)&&("0".equals(value)||"false".equals(value))){
			result = false;
		}
		
		LOGGER.info("配置元素"+key+"的值为："+result);
		return result;
	}
	
	public static Integer getInt(String key){
		LOGGER.info("获取配置元素："+key);
		
		String value = PROP.getProperty(key);
		
		Integer result = null;
		
		try{
			result = Integer.valueOf(value);
		}catch(Exception e){
			LOGGER.error(value+"无法转换为整型");
		}
		
		LOGGER.info("配置元素"+key+"的值为："+value);
		return result;
	}
	
	/**
     * 根据前缀获取符合条件的所有properties属性值。 详细描述：根据前缀进行匹配，找出符合该前缀条件的所有的properties的属性值，以map返回。<br/>
     * 使用方式：PropertiesHolder.getPropertiesByPrefix("前缀")方式调用。
     * 
     * @param prefix properties的key前缀。
     * @return 符合参数条件的map。
     */
    public static Map<String, Object> getPropertiesByPrefix(String prefix) {
    	LOGGER.info("获取匹配{"+prefix+"}规则的属性配置信息");
       
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Set<String> set = PROP.stringPropertyNames();
        for (String key : set) {
            if (key.startsWith(prefix)) {
                resultMap.put(key, getString(key));
            }
        }
        
        LOGGER.info("获取匹配{"+prefix+"}规则的属性配置信息结果为："+resultMap);
        return resultMap;
    }
	
}
