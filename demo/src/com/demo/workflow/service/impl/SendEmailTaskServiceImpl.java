package com.demo.workflow.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.common.constant.ConfigKeys;
import com.demo.common.init.manager.ConfigManager;
import com.demo.common.mapper.UserInfoMapper;
import com.demo.common.model.UserInfo;
import com.demo.common.service.CommonService;
import com.demo.common.util.StringUtils;
import com.demo.common.util.mail.EmailTools;
import com.demo.workflow.service.SendEmailTaskService;

@Service
public class SendEmailTaskServiceImpl implements SendEmailTaskService {
	private static final Logger LOGGER = Logger.getLogger(SendEmailTaskServiceImpl.class);
	
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	public void notify(Task task,String approvers) {
		LOGGER.info("任务到达："+task.getName());
		
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
		if(null!=processInstance){
			Map<String, Object> model = new HashMap<>();
			String[] approvers1 = approvers.split(",");
			for (String userId : approvers1) {
				//查询用户名及邮箱
				UserInfo userInfo = userInfoMapper.getUserInfoById(userId);
				String lastName = "heshaowei";//userInfo.getLastName();
				String email = "heshaowei_code@163.com";//userInfo.getEmail();
				String businessKey = processInstance.getBusinessKey();
				Map<String, String> params = new HashMap<>();
				params.put("taskId", task.getId());
				params.put("procInstId", task.getProcessInstanceId());
				params.put("taskDefKey", task.getTaskDefinitionKey());
				params.put("businessKey", businessKey);
				String checkUrl = getCheckUrl(processInstance.getProcessDefinitionId(),params);
				if(StringUtils.patternEmail(email)&&!StringUtils.isEmpty(lastName)){
					model.put("username", lastName);
					model.put("checkUrl", checkUrl);
					model.put("businessKey", businessKey);
					EmailTools.asynSendEmail(email,"todoWorkCheckTpl.html", model);
				}
			}
		}
		
	}

	private String getCheckUrl(String processDefId,Map<String, String> params) {
		String basePath = ConfigManager.getString("system_base_path");
		if(processDefId.contains("leaveProcess")){
			String vrmCheckPath = ConfigManager.getString("leave_process_check_url");
			String url = basePath+vrmCheckPath+"?c=d";
			Iterator<String> iterator = params.keySet().iterator();
			while(iterator.hasNext()){
				String key = iterator.next();
				String value = params.get(key);
				url += "&"+key+"="+value;
			}
			return url;
		}else{
			return null;
		}
	}

}
