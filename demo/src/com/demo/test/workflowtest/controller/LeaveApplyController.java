package com.demo.test.workflowtest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.common.constant.SystemKeys;
import com.demo.common.controller.BaseController;
import com.demo.common.model.Role;
import com.demo.common.model.UserInfo;
import com.demo.common.response.BaseResponse;
import com.demo.common.util.UserInfoUtil;
import com.demo.test.workflowtest.bean.LeaveApplyInfo;
import com.demo.test.workflowtest.service.LeaveApplyService;
import com.demo.workflow.service.WorkflowService;

@Controller
@RequestMapping("/leaveApply")
public class LeaveApplyController extends BaseController {
	private static final Logger LOGGER = Logger.getLogger(LeaveApplyController.class);
	
	@Autowired
	private LeaveApplyService leaveApplyService;
	@Autowired
	private WorkflowService workflowService;
	
	@RequestMapping("/auth/submitProcess")
	public void submitProcess(@RequestBody LeaveApplyInfo leaveApplyInfo){
		BaseResponse<?> baseResponse = new BaseResponse<>();
		try {
			//
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(SystemKeys.LOGIN_USER_INFO_SESSION_NAME);
			List<Role> roles = userInfo.getRoles();
			Map<String, Object> params = new HashMap<>();
			params.put("isJumpUsertask1", "0");
			for (Role role : roles) {
				if(role.getRoleCode().equals("admin")){
					params.put("isJumpUsertask1", "1");
				}
			}
			params.put("approvers", request.getParameter("approver"));
			int id = leaveApplyService.save(leaveApplyInfo);
			workflowService.startProcess("leaveProcess", 
					"LA"+id, 
					UserInfoUtil.getUserId(), 
					"请假流程", 
					"", 
					params);
			baseResponse.setSuccess(true);
		} catch (Exception e) {
			baseResponse.setMessage(e.getMessage());
			LOGGER.error(e.getMessage(),e);
		}
		print(baseResponse);
	}
}
