package com.demo.common.util.mapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;

public class CustomObjectMapper extends ObjectMapper {
	private static final Logger LOGGER = Logger.getLogger(CustomObjectMapper.class);
	
	public CustomObjectMapper(){  
        CustomSerializerFactory factory = new CustomSerializerFactory();  
        factory.addGenericMapping(Date.class, new JsonSerializer<Date>(){  

			@Override
			public void serialize(Date value, JsonGenerator jsonGenerator,
					SerializerProvider provider) throws IOException,
					JsonProcessingException {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
//				LOGGER.info(sdf.format(value));
                jsonGenerator.writeString(sdf.format(value));  
			}  
        });  
        factory.addGenericMapping(String.class, new JsonSerializer<String>(){

			@Override
			public void serialize(String str, JsonGenerator jsonGenerator,
					SerializerProvider serializerProvider) throws IOException,
					JsonProcessingException {
//				LOGGER.info("转换字符串"+str+",替换单引号为\'");
				str = str.replaceAll("\'", "\\\\'");
//				LOGGER.info("替换后为:"+str);
				jsonGenerator.writeString(str);
			}
        	
        });
        this.setSerializerFactory(factory);  
    } 
}
