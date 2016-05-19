package com.demo.common.init;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;

import com.demo.common.constant.BeanName;
import com.demo.common.constant.SqlKeys;
import com.demo.common.dao.BaseDao;
import com.demo.common.init.manager.ConfigManager;
import com.demo.common.mapper.ConfigMapper;
import com.demo.common.mapper.ResourceMapper;
import com.demo.common.model.Config;
import com.demo.common.model.Resource;

public class ConfigInit {
	private static ConfigInit configInit;
	
	private static ConfigManager configManager;

	private ConfigInit(){};
	
	public static ConfigInit getInstance(){
		if(null==configInit){
			configInit = new ConfigInit();
		}
		return configInit;
	}
	
	public void init(){
		try {
			List<Config> configInfos = BaseDao.getInstance().select(SqlKeys.QUERY_CINFIG);
			for (Config configInfo : configInfos) {
				String key = configInfo.getName();
				String value = configInfo.getValue();
				ConfigManager.setConfigValue(key, value);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
