package com.demo.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

public class ObjectUtil {
	private static final Logger LOGGER = Logger.getLogger(ObjectUtil.class);
	
	/**
	 * 将对象进行深度复制
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Object deepCopy(Object obj) 
    {  
        try {
			// 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝  
			ByteArrayOutputStream bos = new ByteArrayOutputStream();  
  
			ObjectOutputStream oos = new ObjectOutputStream(bos);  
  
			oos.writeObject(obj);  
  
			// 将流序列化成对象  
			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());  
  
			ObjectInputStream ois = new ObjectInputStream(bis);  
  
			return ois.readObject();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
		}  
        return null;
    }  
}
